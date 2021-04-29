package org.quantumclient.qubit.mixin;

import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface IGameRenderer {

    @Accessor
    ShaderEffect getShader();

}
