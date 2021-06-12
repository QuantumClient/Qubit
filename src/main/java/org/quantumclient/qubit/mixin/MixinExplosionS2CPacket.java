package org.quantumclient.qubit.mixin;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.player.Velocity;
import org.quantumclient.qubit.module.render.ESP;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ExplosionS2CPacket.class)
public class MixinExplosionS2CPacket {


    @Shadow @Final private float playerVelocityX;

    @Shadow @Final private float playerVelocityY;

    @Shadow @Final private float playerVelocityZ;

    @Inject(method = "getPlayerVelocityX", at = @At("HEAD"), cancellable = true)
    public void ongetPlayerVelocityX(CallbackInfoReturnable<Float> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Velocity.class)) {
            cir.setReturnValue(playerVelocityX / 100 * ((Velocity) Qubit.getModuleManger().getModule(Velocity.class)).horizontal.getValue().intValue());
        }
    }

    @Inject(method = "getPlayerVelocityY", at = @At("HEAD"), cancellable = true)
    public void ongetPlayerVelocityY(CallbackInfoReturnable<Float> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Velocity.class)) {
            cir.setReturnValue(playerVelocityY / 100 * ((Velocity) Qubit.getModuleManger().getModule(Velocity.class)).vertical.getValue().intValue());
        }
    }

    @Inject(method = "getPlayerVelocityZ", at = @At("HEAD"), cancellable = true)
    public void ongetPlayerVelocityZ(CallbackInfoReturnable<Float> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Velocity.class)) {
            cir.setReturnValue(playerVelocityZ / 100 * ((Velocity) Qubit.getModuleManger().getModule(Velocity.class)).horizontal.getValue().intValue());
        }
    }

}
