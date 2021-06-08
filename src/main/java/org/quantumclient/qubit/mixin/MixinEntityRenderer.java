package org.quantumclient.qubit.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventEntityRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer<T extends Entity> {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void onRender2(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        EventEntityRender event = new EventEntityRender(entity);
        Qubit.getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

}
