package org.quantumclient.qubit.mixin;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import org.quantumclient.energy.EventBus;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventPacketReceive;
import org.quantumclient.qubit.event.EventPacketSend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {

    @Inject(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
    public void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo ci) {
        EventPacketSend event = new EventPacketSend(packet);
        Qubit.getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static void handlePacket(Packet<?> packet, PacketListener listener, CallbackInfo info) {
        EventPacketReceive event = new EventPacketReceive(packet);
        Qubit.getEventBus().post(event);
        if (event.isCancelled()) info.cancel();
    }

}
