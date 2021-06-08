package org.quantumclient.qubit.module.player;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@SetCategory(Category.PLAYER)
@Info(value = "NoFall", description = "Prevents fall damage")
public class NoFall extends Module {

    @Subscribe
    public void onTick(EventTick event) {
        if (mc.player.fallDistance > 4) {
            mc.player.fallDistance = 0;
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 1, mc.player.getZ(), true));
        }
    }
}
