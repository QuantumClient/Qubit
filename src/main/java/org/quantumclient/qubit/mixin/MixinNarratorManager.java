package org.quantumclient.qubit.mixin;

import net.minecraft.client.util.NarratorManager;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.managers.ModuleManager;
import org.quantumclient.qubit.module.player.AntiNarrator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NarratorManager.class)
public class MixinNarratorManager {

    @Inject(method = "isActive", at = @At("HEAD"), cancellable = true)
    private void onisActive(CallbackInfoReturnable<Boolean> callback) {
        if (Qubit.getModuleManger().isModuleEnabled(AntiNarrator.class))
            callback.setReturnValue(false);
    }
}
