package org.quantumclient.qubit.module.render;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;

public class Fullbight extends Module {

    private StatusEffectInstance vision;

    public Fullbight() {
        super("Fullbight", Category.RENDER);
    }

    @Subscribe
    public void onTick(EventTick event) {
        vision = new StatusEffectInstance(StatusEffects.NIGHT_VISION, 100);
        mc.player.applyStatusEffect(vision);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (vision != null)
         mc.player.removeStatusEffectInternal(vision.getEffectType());
    }
}
