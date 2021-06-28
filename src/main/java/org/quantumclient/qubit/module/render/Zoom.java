package org.quantumclient.qubit.module.render;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.commons.annoations.Silent;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventHudRender;
import org.quantumclient.qubit.event.EventRenderTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.numbers.DoubleSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.Bind;
import org.quantumclient.qubit.utils.annotations.AddSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Silent
@Info("Zoom")
@SetCategory(Category.RENDER)
public class Zoom extends Module {

    @AddSetting
    public final DoubleSetting zoomSetting = new DoubleSetting("Amount", 10D, 1D, 50D, 1);

    public Zoom() {
        super();
        this.bind = new Bind(GLFW.GLFW_KEY_C, 0, Bind.Type.HOLD);
    }

}
