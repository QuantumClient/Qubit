package org.quantumclient.qubit.module.render;

import net.minecraft.particle.ParticleTypes;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventAddParticle;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;

public class NoRender extends Module {

    private CheckSetting explosion = new CheckSetting("Explosion", true);

    public NoRender() {
        super("NoRender", Category.RENDER);
        addSetting(explosion);
    }

    @Subscribe
    public void onAddParticle(EventAddParticle event) {
        if (event.getParticleEffect() == ParticleTypes.EXPLOSION && explosion.getValue()) event.setCancelled(true);

    }

}
