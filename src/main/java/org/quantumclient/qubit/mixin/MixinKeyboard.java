package org.quantumclient.qubit.mixin;

import net.minecraft.client.Keyboard;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventKeyPress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKeyPress(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
        EventKeyPress event = new EventKeyPress(key, i, j);
        Qubit.getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

}
