package org.quantumclient.qubit.module.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventHudRender;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;

import java.awt.*;


public class TestModule extends Module {

    private CheckSetting booleanTest = new CheckSetting("BooleanTest", "this is a test",true);
    private FloatSetting numbertest = new FloatSetting("numbertesta", 4.4f, 4f, 10f, 0f, 1);
    private ModeSetting modTest = new ModeSetting("ModeTest", "Packet", new String[] {"Packet", "Normal"});


    public TestModule() {
        super("TestModule", Category.CLIENT);
        addSetting(booleanTest);
        addSetting(numbertest);
        addSetting(modTest);
    }

    @Subscribe
    public void onHudRender(EventHudRender event) {
        if (numbertest.getValue() instanceof Float) {
       //     mc.textRenderer.draw(event.getMatrix(), "Hi this is a test", 100, 100, -1);
        }

        //Qubit.getFontManger().drawString(event.getMatrix(), "Hi this is a test", 100, 100, true);
    }

}
