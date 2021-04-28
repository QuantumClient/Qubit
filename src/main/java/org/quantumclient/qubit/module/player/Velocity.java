package org.quantumclient.qubit.module.player;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventPacketReceive;
import org.quantumclient.qubit.mixin.IEntityVelocityUpdateS2CPacket;
import org.quantumclient.qubit.mixin.IExplosionS2CPacket;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.AddSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;

public class Velocity extends Module {

    @AddSetting
    private FloatSetting horizontal = new FloatSetting("Horizontal", 0f, 0f, 100f, 0, 0);

    @AddSetting
    private FloatSetting vertical = new FloatSetting("Vertical", 0f, 0f, 100f, 0, 0);


    public Velocity() {
        super("Velocity", Category.PLAYER);
    }

    @Subscribe
    public void onReceive(EventPacketReceive event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacket packet = (EntityVelocityUpdateS2CPacket) event.getPacket();
            ((IEntityVelocityUpdateS2CPacket) packet).setVelocityX(packet.getVelocityX() / 100 * horizontal.getValue().intValue());
            ((IEntityVelocityUpdateS2CPacket) packet).setVelocityY(packet.getVelocityY() / 100 * vertical.getValue().intValue());
            ((IEntityVelocityUpdateS2CPacket) packet).setVelocityZ(packet.getVelocityZ() / 100 * horizontal.getValue().intValue());
        }

        if (event.getPacket() instanceof ExplosionS2CPacket) {
            ExplosionS2CPacket packet = (ExplosionS2CPacket) event.getPacket();
            ((IExplosionS2CPacket) packet).setPlayerVelocityX(packet.getPlayerVelocityX() / 100 * horizontal.getValue().intValue());
            ((IExplosionS2CPacket) packet).setPlayerVelocityY(packet.getPlayerVelocityY() / 100 * vertical.getValue().intValue());
            ((IExplosionS2CPacket) packet).setPlayerVelocityZ(packet.getPlayerVelocityZ() / 100 * horizontal.getValue().intValue());
        }
    }

}
