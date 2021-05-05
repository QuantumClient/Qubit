package org.quantumclient.qubit.module.render;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventEntityRender;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.AddSetting;
import org.quantumclient.qubit.settings.CheckSetting;

public class ESP extends Module {

    @AddSetting
    private final CheckSetting players = new CheckSetting("Players", true);

    @AddSetting
    private final CheckSetting mobs = new CheckSetting("Mobs", true);

    @AddSetting
    private final CheckSetting others = new CheckSetting("Others", true);

    public ESP() {
        super("ESP", Category.RENDER);
    }

    @Subscribe
    public void onEntityRender(EventEntityRender event) {
        if (event.getEntity() instanceof PlayerEntity && players.getValue() && event.getEntity() != mc.player ) {
            event.getEntity().setGlowing(true);
        }
        if (event.getEntity() instanceof HostileEntity && mobs.getValue() && event.getEntity() != mc.player ) {
            event.getEntity().setGlowing(true);
        }
        if (others.getValue()) {
            event.getEntity().setGlowing(true);
        }
    }

}
