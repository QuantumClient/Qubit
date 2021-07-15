package org.quantumclient.qubit.module.client;

import net.minecraft.block.Blocks;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventHudRender;
import org.quantumclient.qubit.event.EventBlockRender;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.Bind;
import org.quantumclient.qubit.utils.annotations.SetCategory;


@SetCategory(Category.CLIENT)
@Info("TestModule")
public class TestModule extends Module {

    private final CheckSetting booleanTest = new CheckSetting("BooleanTest", "this is a test",true);
    private final FloatSetting numbertest = new FloatSetting("numbertesta", 4.4f, 4f, 10f, 1);
    private final ModeSetting modTest = new ModeSetting("ModeTest", "Packet", new String[] {"Packet", "Normal"});

    public TestModule() {
        super();
        addSetting(booleanTest);
        addSetting(numbertest);
        addSetting(modTest);
        this.bind = new Bind(0, 0, Bind.Type.HOLD);
    }

    @Subscribe
    public void onHudRender(EventHudRender event) {
        if (numbertest.getValue() instanceof Float) {
       //     mc.textRenderer.draw(event.getMatrix(), "Hi this is a test", 100, 100, -1);
        }

        //Qubit.getFontManger().drawString(event.getMatrix(), "Hi this is a test", 100, 100, true);
    }


}
