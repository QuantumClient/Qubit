package org.quantumclient.qubit.module.player;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", "Prevents fall damage", Category.PLAYER);
    }

    @Subscribe
    public void onTick(EventTick event) {
        if (mc.player.fallDistance > 4) {
            mc.player.fallDistance = 0;
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(mc.player.getX(), mc.player.getY() + 1, mc.player.getZ(), true));
        }
    }
}
