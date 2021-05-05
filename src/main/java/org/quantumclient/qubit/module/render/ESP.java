package org.quantumclient.qubit.module.render;

import net.minecraft.entity.Entity;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventEntityRender;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;

import java.awt.*;

public class ESP extends Module {

    public ESP() {
        super("ESP", Category.RENDER);
    }

    @Subscribe
    public void onEntityRender(EventEntityRender event) {
        event.getEntity().setGlowing(true);
    }

    public Color getEntityColor(Entity entity) {
        return Color.RED;
    }
}
