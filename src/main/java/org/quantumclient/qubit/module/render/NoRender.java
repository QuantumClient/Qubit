package org.quantumclient.qubit.module.render;

import net.minecraft.particle.ParticleTypes;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventAddParticle;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;

public class NoRender extends Module {

    private CheckSetting explosion = new CheckSetting("Explosion", true);
    private CheckSetting campFire = new CheckSetting("CampFire", true);

    public NoRender() {
        super("NoRender", Category.RENDER);
        addSetting(explosion, campFire);
    }

    @Subscribe
    public void onAddParticle(EventAddParticle event) {
        if (event.getParticleEffect() == ParticleTypes.EXPLOSION && explosion.getValue()) event.setCancelled(true);

        if ((event.getParticleEffect() == ParticleTypes.CAMPFIRE_COSY_SMOKE || event.getParticleEffect() == ParticleTypes.CAMPFIRE_SIGNAL_SMOKE || event.getParticleEffect() == ParticleTypes.SMOKE) && campFire.getValue())
            event.setCancelled(true);

    }

}
