package org.quantumclient.qubit.module.combat;

import com.google.common.collect.Streams;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;
import org.utilitymods.friendapi.FriendManager;

import java.util.List;
import java.util.stream.Collectors;

@SetCategory(Category.COMBAT)
@Info(value = "Aura", description = "Kill people")
public class Aura extends Module {

    private final ModeSetting modeSetting = new ModeSetting("Mode", "Multi", new String[]{"Multi", "Single"});
    private final CheckSetting players = new CheckSetting("Players", true);
    private final CheckSetting mobs = new CheckSetting("Mobs", true);
    private final FloatSetting range = new FloatSetting("Range", 5f, 1f, 10f, 0);

    private List<Entity> entityList;
    private int tick;

    public Aura() {
        super();
        addSetting(modeSetting,
                players,
                mobs,
                range);
    }

    @Subscribe
    public void onTick(EventTick event) {
        tick++;

        if (mc.player.getAttackCooldownProgress(0) < 1) return;
        if (tick >= 10) {
            entityList = Streams.stream(mc.world.getEntities())
                    .filter(e -> e instanceof LivingEntity)
                    .filter(e -> (e instanceof PlayerEntity && players.getValue() && !e.equals(mc.player) && !FriendManager.INSTANCE.isFriend(((PlayerEntity) e).getGameProfile().getId()))
                            || (e instanceof Monster && mobs.getValue()))
                    .filter(e -> e.distanceTo(mc.player) <= range.getValue())
                    .sorted((a, b) -> Float.compare(a.distanceTo(mc.player), b.distanceTo(mc.player)))
                    .collect(Collectors.toList());
        }


        if (entityList == null) return;
        if (entityList.isEmpty()) return;
        switch (modeSetting.getValue()) {
            case "Multi":
                int i = 0;
                for (Entity entity : entityList) {
                    if (i >= 10) return;
                    mc.interactionManager.attackEntity(mc.player, entity);
                    i++;
                }
                mc.player.swingHand(Hand.MAIN_HAND);
                break;
            case "Single":
                mc.interactionManager.attackEntity(mc.player, entityList.get(0));
                mc.player.swingHand(Hand.MAIN_HAND);
                break;
        }

        if (tick >= 21) {
            entityList.clear();
            tick = 0;
        }
    }
}
