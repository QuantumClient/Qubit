package org.quantumclient.qubit.module.client;

import org.lwjgl.glfw.GLFW;
import org.quantumclient.qubit.gui.click.ClickGuiScreen;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGui", "Opens a control panel for modules", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.CLIENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.player == null) return;
        mc.openScreen(new ClickGuiScreen());
        toggle();
    }

}
