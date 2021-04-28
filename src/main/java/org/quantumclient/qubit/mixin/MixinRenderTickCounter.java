package org.quantumclient.qubit.mixin;

import net.minecraft.client.render.RenderTickCounter;
import org.quantumclient.energy.EventBus;
import org.quantumclient.qubit.event.EventRenderTick;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTickCounter.class)
public class MixinRenderTickCounter {

    @Shadow
    public float lastFrameDuration;

    @Inject(method = "beginRenderTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J"))
    public void onBeingRenderTick(long timeMillis, CallbackInfoReturnable<Integer> cir) {
        EventRenderTick event = new EventRenderTick(lastFrameDuration);
        EventBus.post(event);
        lastFrameDuration = event.getLastFrameDuration();
    }

}
