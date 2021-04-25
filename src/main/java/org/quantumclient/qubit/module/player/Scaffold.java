package org.quantumclient.qubit.module.player;

import net.minecraft.item.BlockItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.mixin.ILivingEntity;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;

public class Scaffold extends Module {

    private final CheckSetting tower = new CheckSetting("Tower", true);
    private final CheckSetting swing = new CheckSetting("Swing", "Swing hand when placing blocks", false);
    private final CheckSetting switchItem = new CheckSetting("Switch", "Switch to block item", false);
    private final FloatSetting extra = new FloatSetting("Extra", "Places blocks in front of you", 0f, 0f, 8f, 0, 0);

    public Scaffold() {
        super("Scaffold", "Places blocks under you", Category.PLAYER);
        addSetting(tower,
                swing,
                switchItem,
                extra
        );
    }

    @Subscribe
    public void onTick(EventTick event) {
        if (mc.player == null || mc.world == null) return;

        if (switchItem.getValue() && !(mc.player.getMainHandStack().getItem() instanceof BlockItem)) for (int i = 0; i < 9; ++i) {
            if (mc.player.inventory.getStack(i).getItem() instanceof BlockItem) {
                mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(i));
                break;
            }
        }
        BlockPos blockPos = getBlockPos();

        if (((ILivingEntity) mc.player).getJumping() && tower.getValue()) {
            mc.player.setVelocity(new Vec3d(0, 0.3, 0));
        }

        BlockHitResult blockHitResult = new BlockHitResult(new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()), Direction.DOWN, blockPos, false);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            mc.getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, blockHitResult));
            if (swing.getValue()) {
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }

    }

    private boolean canPlace(BlockPos blockPos) {
        return mc.world.getBlockState(blockPos).getMaterial().isReplaceable() && mc.world.getOtherEntities(null, new Box(blockPos)).isEmpty();
    }

    private BlockPos getBlockPos() {

        if (canPlace(new BlockPos(mc.player.getX(), mc.player.getY() - 1, mc.player.getZ()))) {
            return new BlockPos(Math.floor(mc.player.getX()), mc.player.getY() - 1, Math.floor(mc.player.getZ()));
        } else {
            float speed = mc.player.forwardSpeed;
            float sideSpeed = mc.player.sidewaysSpeed;
            double cos = Math.cos(Math.toRadians(mc.player.yaw + 90.0f));
            double sin = Math.sin(Math.toRadians(mc.player.yaw + 90.0f));
            double x;
            double z;
            int i = 1;
            BlockPos tempBlock = new BlockPos(mc.player.getX(), mc.player.getY() - 1, mc.player.getZ());
            while (!canPlace(tempBlock)) {
                if (i > extra.getValue()) {
                    break;
                }
                x = mc.player.getX() + (speed * 1.0 * cos + sideSpeed * 1.0 * sin) * i;
                z = mc.player.getZ() + (speed * 1.0 * sin - sideSpeed * 1.0 * cos) * i;
                tempBlock = new BlockPos(x, mc.player.getY() - 1, z);
                i++;
            }
            return tempBlock;
        }
    }

}