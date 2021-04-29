package org.quantumclient.qubit.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.quantumclient.energy.EventBus;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventHudRender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (client.player == null || client.world == null) return;
        EventHudRender event = new EventHudRender(matrices);
        Qubit.getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

}
