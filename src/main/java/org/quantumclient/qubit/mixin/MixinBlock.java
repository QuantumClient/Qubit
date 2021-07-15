package org.quantumclient.qubit.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.world.Xray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void onShouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Xray.class)) {
            cir.setReturnValue(Qubit.getModuleManger().getModule(Xray.class).isinList(state.getBlock()));
        }
    }

}
