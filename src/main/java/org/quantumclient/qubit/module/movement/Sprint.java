package org.quantumclient.qubit.module.movement;

import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@SetCategory(Category.MOVEMENT)
@Info(value = "Sprint", description = "Automatically run")
public class Sprint extends Module {

    private final CheckSetting test = new CheckSetting("bittest", true);
    private final FloatSetting numbertest = new FloatSetting("numbertesta", 4.4f, 1f, 10f, 0f, 1);

    public Sprint() {
        super();
        addSetting(test);
        addSetting(numbertest);
    }

    @Subscribe
    public void onTick(EventTick event) {
        mc.player.setSprinting(mc.player.input.movementForward > 0 && mc.player.input.movementSideways != 0 ||
                mc.player.input.movementForward > 0 && !mc.player.isSneaking());
    }

}
