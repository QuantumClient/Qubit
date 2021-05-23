package org.quantumclient.qubit.module.movement;

import net.minecraft.util.math.Vec3d;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@SetCategory(Category.MOVEMENT)
@Info(value = "ElytraFlight", description = "Fly with your elytra without needing rockets")
public class ElytraFlight extends Module {

    @Subscribe
    public void onTick(EventTick event) {
        if (!mc.player.isFallFlying()) return;
        Vec3d vec = Vec3d.ZERO;
        if (!mc.options.keySneak.isPressed() && !mc.options.keyJump.isPressed()) {
            vec = new Vec3d(0, .1, 0);
        }
        mc.player.setVelocity(vec);
    }




}
