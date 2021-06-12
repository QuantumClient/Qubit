package org.quantumclient.qubit.mixin;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.player.Velocity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityVelocityUpdateS2CPacket.class)
public class MixinEntityVelocityUpdateS2CPacket {

    @Shadow @Final private int velocityX;

    @Shadow @Final private int velocityZ;

    @Shadow @Final private int velocityY;

    @Inject(method = "getVelocityX", at = @At("HEAD"), cancellable = true)
    public void ongetVelocityX(CallbackInfoReturnable<Integer> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Velocity.class)) {
            cir.setReturnValue(velocityX / 100 * ((Velocity) Qubit.getModuleManger().getModule(Velocity.class)).horizontal.getValue().intValue());
        }
    }

    @Inject(method = "getVelocityY", at = @At("HEAD"), cancellable = true)
    public void ongetVelocityY(CallbackInfoReturnable<Integer> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Velocity.class)) {
            cir.setReturnValue(velocityY / 100 * ((Velocity) Qubit.getModuleManger().getModule(Velocity.class)).vertical.getValue().intValue());
        }
    }

    @Inject(method = "getVelocityZ", at = @At("HEAD"), cancellable = true)
    public void ongetVelocityZ(CallbackInfoReturnable<Integer> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Velocity.class)) {
            cir.setReturnValue(velocityZ / 100 * ((Velocity) Qubit.getModuleManger().getModule(Velocity.class)).horizontal.getValue().intValue());
        }
    }

}
