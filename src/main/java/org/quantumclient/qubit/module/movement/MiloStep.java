package org.quantumclient.qubit.module.movement;

import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.annotations.AddSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@SetCategory(Category.MOVEMENT)
@Info(value = "MiloStep", description = "Step made by the one and only milo!!")
public class MiloStep extends Module {

    @AddSetting
    private FloatSetting height = new FloatSetting("Height", 2f, 0f, 3f, 0);

    @Override
    protected void onDisable() {
        super.onDisable();
        if (mc.player != null)
        mc.player.stepHeight = 0f;
    }

    @Subscribe
    public void onTick(EventTick event) {
        if(mc.world == null || mc.player.isInLava() || mc.player.isInsideWaterOrBubbleColumn() || mc.player.isSubmergedInWater() || mc.player == null){
            return;
        }

        if(mc.player.isOnGround()){
            mc.player.stepHeight = height.getValue();
        }
    }
}
