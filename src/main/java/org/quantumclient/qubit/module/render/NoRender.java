package org.quantumclient.qubit.module.render;

import net.minecraft.particle.ParticleTypes;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventAddParticle;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Info("NoRender")
@SetCategory(Category.RENDER)
public class NoRender extends Module {

    private final CheckSetting explosion = new CheckSetting("Explosion", true);

    public NoRender() {
        super();
        addSetting(explosion);
    }

    @Subscribe
    public void onAddParticle(EventAddParticle event) {
        if (event.getParticleEffect() == ParticleTypes.EXPLOSION && explosion.getValue()) event.setCancelled(true);

    }

}
