package org.quantumclient.qubit.module.render;

import net.minecraft.util.Identifier;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Info("Australia")
@SetCategory(Category.RENDER)
public class Australia extends Module {

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.gameRenderer != null)
            mc.gameRenderer.loadShader(new Identifier("shaders/post/flip.json"));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.world == null) return;
        if (mc.gameRenderer.getShader() != null) {
            mc.gameRenderer.getShader().close();
        }
    }

}
