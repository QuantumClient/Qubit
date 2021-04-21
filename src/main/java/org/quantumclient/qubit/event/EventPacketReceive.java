package org.quantumclient.qubit.event;

import net.minecraft.network.Packet;
import org.quantumclient.energy.Event;

public class EventPacketReceive extends Event {
    private Packet<?> packet;

    public EventPacketReceive(Packet<?> packet) {
        this.packet = packet;

    }

    public Packet<?> getPacket() {
        return packet;
    }

}
