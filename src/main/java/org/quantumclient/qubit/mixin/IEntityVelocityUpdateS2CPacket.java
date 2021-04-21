package org.quantumclient.qubit.mixin;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityVelocityUpdateS2CPacket.class)
public interface IEntityVelocityUpdateS2CPacket {

    @Accessor
    void setVelocityX(int x);

    @Accessor
    void setVelocityY(int y);

    @Accessor
    void setVelocityZ(int z);

}
