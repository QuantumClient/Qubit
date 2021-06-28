package org.quantumclient.qubit.mixin;

import net.minecraft.client.render.GameRenderer;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.render.Zoom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    public void getZoomLevel(CallbackInfoReturnable<Double> ci) {
        if (Qubit.getModuleManger().isModuleEnabled(Zoom.class)) {
            ci.setReturnValue(ci.getReturnValue() / ((Zoom) Qubit.getModuleManger().getModule(Zoom.class)).zoomSetting.getValue());
        }
    }

}
