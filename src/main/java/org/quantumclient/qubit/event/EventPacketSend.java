package org.quantumclient.qubit.event;

import net.minecraft.network.Packet;
import org.quantumclient.energy.Event;

public class EventPacketSend extends Event {
    private final Packet<?> packet;

    public EventPacketSend(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

}
