package org.quantumclient.qubit.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventAddParticle;
import org.quantumclient.qubit.event.EventWorldRender;
import org.quantumclient.qubit.module.render.ESP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.io.IOException;

import static org.quantumclient.qubit.utils.Wrapper.mc;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

    @Shadow
    MinecraftClient client;

    @Shadow
    ShaderEffect entityOutlineShader;

    @Shadow
    Framebuffer entityOutlinesFramebuffer;


    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;ZDDDDDD)V", at = @At("HEAD"), cancellable = true)
    public void send(ParticleEffect parameters, boolean shouldAlwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfo ci) {
        EventAddParticle event = new EventAddParticle(parameters);
        Qubit.getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

//    I was trying with a shader esp.... (no work) :(
    @Inject(method = "loadEntityOutlineShader", at = @At("HEAD"), cancellable = true)
    public void onLoadEntityShader(CallbackInfo ci) {
        ci.cancel();
        Identifier identifier = new Identifier("qubit", "shaders/post/custom_entity_outline.json");
        try {
            entityOutlineShader = new ShaderEffect(this.client.getTextureManager(), this.client.getResourceManager(), this.client.getFramebuffer(), identifier);
            entityOutlineShader.setupDimensions(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
            entityOutlinesFramebuffer = this.entityOutlineShader.getSecondaryTarget("final");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getTeamColorValue()I"))
    public int getColor(Entity entity) {
        if (Qubit.getModuleManger().isModuleEnabled(ESP.class)) {
            if (entity instanceof PlayerEntity) {
                if (Qubit.getFriendManger().isFriend((PlayerEntity) entity)) {
                    return 5636095;
                } else {
                    return 16733525;
                }
            }
            if (entity instanceof HostileEntity) {
                return 170;
            }
        }
        return 16777215;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;hasOutline(Lnet/minecraft/entity/Entity;)Z"))
    public boolean hasOutline(MinecraftClient minecraftClient, Entity entity) {
        if (Qubit.getModuleManger().isModuleEnabled(ESP.class)) {
            ESP esp = (ESP) Qubit.getModuleManger().getModule(ESP.class);
            if (entity instanceof PlayerEntity && (esp.players.getValue() && entity != minecraftClient.player)) {
                return true;
            }
            if (entity instanceof HostileEntity && esp.mobs.getValue()) {
                return true;
            }
            if (esp.others.getValue() && entity != minecraftClient.player) {
                return true;
            }
        }
        return entity.isGlowing() || minecraftClient.player != null && minecraftClient.player.isSpectator() && minecraftClient.options.keySpectatorOutlines.isPressed() && entity.getType() == EntityType.PLAYER;
    }


    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void onRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        EventWorldRender event = new EventWorldRender(matrices, tickDelta);
        Qubit.getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }



}