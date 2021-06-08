package org.quantumclient.qubit.module.render;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Info("Fullbright")
@SetCategory(Category.RENDER)
public class Fullbright extends Module {

    private StatusEffectInstance vision;

    @Subscribe
    public void onTick(EventTick event) {
        vision = new StatusEffectInstance(StatusEffects.NIGHT_VISION, 100);
        mc.player.setStatusEffect(vision, mc.player);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (vision != null)
         mc.player.removeStatusEffectInternal(vision.getEffectType());
    }
}
