package org.quantumclient.qubit.module.world;

import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventRenderTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.numbers.FloatSetting;

public class Timer extends Module {

    private FloatSetting timerSetting = new FloatSetting("Timer", 1f, 0f, 20f, 0, 2);

    public Timer() {
        super("Timer", Category.WORLD);
        addSetting(timerSetting);
    }

    @Subscribe
    public void onRenderTick(EventRenderTick event) {
        float eventLastFrameDuration = event.getLastFrameDuration();
        event.setLastFrameDuration(eventLastFrameDuration *= timerSetting.getValue());
    }

}
