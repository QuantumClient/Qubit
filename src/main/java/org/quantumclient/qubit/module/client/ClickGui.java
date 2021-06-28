package org.quantumclient.qubit.module.client;

import org.lwjgl.glfw.GLFW;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.commons.annoations.Silent;
import org.quantumclient.qubit.gui.click.ClickGuiScreen;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Silent
@SetCategory(Category.CLIENT)
@Info(value = "ClickGui", description = "Opens a control panel for modules", bind = GLFW.GLFW_KEY_RIGHT_SHIFT )
public class ClickGui extends Module {

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.player == null) return;
        //mc.gameRenderer.loadShader(new Identifier("qubit", "shaders/post/gui_blur.json"));
        mc.openScreen(new ClickGuiScreen());

        toggle();
    }

}
