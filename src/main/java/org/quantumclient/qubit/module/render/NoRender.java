package org.quantumclient.qubit.module.render;

import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventAddParticle;
import org.quantumclient.qubit.event.EventPacketReceive;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.utils.annotations.AddSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Info("NoRender")
@SetCategory(Category.RENDER)
public class NoRender extends Module {

    @AddSetting
    private final CheckSetting explosion = new CheckSetting("Explosion", true);

    @AddSetting
    public final CheckSetting pumpkin = new CheckSetting("Pumpkink", true);

    @AddSetting
    private final CheckSetting skylight = new CheckSetting("skylight", true);

    @Subscribe
    public void onAddParticle(EventAddParticle event) {
        if (event.getParticleEffect() == ParticleTypes.EXPLOSION && explosion.getValue()) event.setCancelled(true);
    }

    @Subscribe
    public void onReceivePacket(EventPacketReceive event) {
        if (event.getPacket() instanceof LightUpdateS2CPacket && skylight.getValue()) event.cancel();
    }

}
