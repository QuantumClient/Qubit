package org.quantumclient.qubit.module.render;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.utils.annotations.AddSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Info("Fullbright")
@SetCategory(Category.RENDER)
public class Fullbright extends Module {

    @AddSetting
    private final ModeSetting mode = new ModeSetting("Mode", "Effect", new String[] {"Effect", "Gamma"});

    private StatusEffectInstance vision;

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Subscribe
    public void onTick(EventTick event) {
        if (mode.getValue().equals("Effect")) {
            vision = new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2);
            mc.player.setStatusEffect(vision, mc.player);
        }
        if (mode.getValue().equals("Gamma")) {
            setGamma(100D);
        } else if (mc.options.gamma != 0D) {
            setGamma(0D);
        }

    }

    private void setGamma(Double gamma) {
        if (mc.options.gamma < gamma) {
            mc.options.gamma += 0.3;
        } else {
            mc.options.gamma -= 0.3;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (vision != null)
            mc.player.removeStatusEffectInternal(vision.getEffectType());
        if (mc.options != null) {
            if (mc.options.gamma != 0D) mc.options.gamma = 0D;

        }

    }

}
