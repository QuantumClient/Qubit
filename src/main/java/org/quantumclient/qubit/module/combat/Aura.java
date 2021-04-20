package org.quantumclient.qubit.module.combat;

import com.google.common.collect.Streams;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;

import java.util.List;
import java.util.stream.Collectors;

public class Aura extends Module {

    private ModeSetting modeSetting = new ModeSetting("Mode", "Multi", new String[]{"Multi", "Single"});
    private CheckSetting players = new CheckSetting("Players", true);
    private CheckSetting mobs = new CheckSetting("Mobs", true);
    private FloatSetting range = new FloatSetting("Range", 5f, 1f, 10f, 0, 0);

    private List<Entity> entityList;

    public Aura() {
        super("Aura", "Kill people", Category.COMBAT);
        addSetting(modeSetting,
                players,
                mobs,
                range);
    }

    @Subscribe
    public void onTick(EventTick event) {

        if (mc.player.getAttackCooldownProgress(0) < 1) return;
        entityList = Streams.stream(mc.world.getEntities())
                .filter(e -> e instanceof LivingEntity)
                .filter(e -> (e instanceof PlayerEntity && players.getValue() && !e.equals(mc.player) && !Qubit.getFriendManger().isFriend((PlayerEntity) e))
                        || (e instanceof Monster && mobs.getValue()))
                .filter(e -> e.distanceTo(mc.player) <= range.getValue())
                .sorted((a, b) -> Float.compare(a.distanceTo(mc.player), b.distanceTo(mc.player)))
                .collect(Collectors.toList());

        if (entityList.isEmpty()) return;
        switch (modeSetting.getValue()) {
            case "Multi":
                for (Entity entity : entityList) {
                    mc.interactionManager.attackEntity(mc.player, entity);
                }
                mc.player.swingHand(Hand.MAIN_HAND);
                break;
            case "Single":
                mc.interactionManager.attackEntity(mc.player, entityList.get(0));
                mc.player.swingHand(Hand.MAIN_HAND);
                break;
        }

        entityList.clear();
    }
}
