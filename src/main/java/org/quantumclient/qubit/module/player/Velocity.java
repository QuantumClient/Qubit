package org.quantumclient.qubit.module.player;

import org.quantumclient.commons.annoations.Info;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.annotations.AddSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Info("Velocity")
@SetCategory(Category.PLAYER)
public class Velocity extends Module {

    @AddSetting
    public final FloatSetting horizontal = new FloatSetting("Horizontal", 0f, 0f, 100f, 0, 0);

    @AddSetting
    public final FloatSetting vertical = new FloatSetting("Vertical", 0f, 0f, 100f, 0, 0);
/**
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
    */

}
