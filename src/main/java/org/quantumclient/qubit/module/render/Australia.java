package org.quantumclient.qubit.module.render;

import net.minecraft.util.Identifier;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;

public class Australia extends Module {

    public Australia() {
        super("Australia", Category.RENDER);
    }

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
