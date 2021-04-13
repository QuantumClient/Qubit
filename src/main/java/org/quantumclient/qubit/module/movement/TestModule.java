package org.quantumclient.qubit.module.movement;

import org.lwjgl.glfw.GLFW;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventHudRender;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;


public class TestModule extends Module {

    private CheckSetting booleanTest = new CheckSetting("BooleanTest", true);
    private FloatSetting numbertest = new FloatSetting("numbertesta", 4.4f, 4f, 10f, 0f, 1);
    private ModeSetting modTest = new ModeSetting("ModeTest", "Packet", new String[] {"Packet", "Normal"});


    public TestModule() {
        super("TestModule", Category.MOVEMENT, GLFW.GLFW_KEY_D);
        addSetting(booleanTest);
        addSetting(numbertest);
        addSetting(modTest);
    }

    @Subscribe
    public void onHudRender(EventHudRender event) {
        if (numbertest.getValue() instanceof Float) {
            mc.textRenderer.draw(event.getMatrix(), "Hi this is a test", 100, 100, -1);
        }

    }

}
