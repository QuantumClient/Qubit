package org.quantumclient.qubit.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.world.Xray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinAbstractBlockState {

    @Shadow public abstract Block getBlock();

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void onRender(CallbackInfoReturnable<BlockRenderType> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Xray.class) && !Qubit.getModuleManger().getModule(Xray.class).isinList(getBlock())) {
            cir.setReturnValue(BlockRenderType.INVISIBLE);
        }
    }

    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    public void onGetLuminance(CallbackInfoReturnable<Integer> cir) {
        if (Qubit.getModuleManger().isModuleEnabled(Xray.class)) {
            cir.setReturnValue(100);
        }
    }
}
