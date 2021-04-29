package org.quantumclient.qubit.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.particle.ParticleEffect;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventAddParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;ZDDDDDD)V", at = @At("HEAD"), cancellable = true)
    public void send(ParticleEffect parameters, boolean shouldAlwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfo ci) {
        EventAddParticle event = new EventAddParticle(parameters);
        Qubit.getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }


}
