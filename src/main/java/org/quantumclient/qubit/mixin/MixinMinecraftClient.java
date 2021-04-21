package org.quantumclient.qubit.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;
import org.quantumclient.energy.EventBus;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.utils.FontUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Shadow
    public ClientWorld world;

    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (player != null && world != null) EventBus.post(new EventTick());
    }

    @Inject(method = "updateWindowTitle", at = @At("HEAD"), cancellable = true)
    public void onTitleChange(CallbackInfo ci) {
        FontUtils.init();
        ci.cancel();
    }

    @Inject(method = "getTextureManager", at = @At("HEAD"))
    public void onTextureManager(CallbackInfoReturnable<TextureManager> cir) {
        FontUtils.init();
    }

}
