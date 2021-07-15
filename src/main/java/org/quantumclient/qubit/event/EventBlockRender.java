package org.quantumclient.qubit.event;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.quantumclient.energy.Event;

public class EventBlockRender extends Event {

    private BlockState state;
    private BlockPos pos;

    public EventBlockRender(BlockState state, BlockPos pos) {
        this.state = state;
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }
}
