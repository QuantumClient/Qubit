package org.quantumclient.qubit.event;

import net.minecraft.client.util.math.MatrixStack;
import org.quantumclient.energy.Event;

public class EventWorldRender extends Event {

    private MatrixStack matrixStack;
    private float tickDelay;

    public EventWorldRender(MatrixStack matrixStack, float tickDelay) {
        this.matrixStack = matrixStack;
        this.tickDelay = tickDelay;
    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }

    public float getTickDelay() {
        return tickDelay;
    }

}
