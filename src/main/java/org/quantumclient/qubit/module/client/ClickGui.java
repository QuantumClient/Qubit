package org.quantumclient.qubit.module.client;

import net.minecraft.util.Identifier;
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
        mc.gameRenderer.loadShader(new Identifier("qubit", "shaders/post/gui_blur.json"));
        mc.openScreen(new ClickGuiScreen());
        toggle();
    }

}
