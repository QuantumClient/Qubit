package org.quantumclient.qubit.mixin;

import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExplosionS2CPacket.class)
public interface IExplosionS2CPacket {

    @Accessor
    void setPlayerVelocityX(float x);

    @Accessor
    void setPlayerVelocityY(float y);

    @Accessor
    void setPlayerVelocityZ(float z);

}
