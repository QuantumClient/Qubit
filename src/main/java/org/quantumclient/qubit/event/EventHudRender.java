package org.quantumclient.qubit.event;

import net.minecraft.client.util.math.MatrixStack;
import org.quantumclient.energy.Event;

public class EventHudRender extends Event {

    private MatrixStack matrix;

    public EventHudRender(MatrixStack matrix) {
        this.matrix = matrix;
    }

    public MatrixStack getMatrix() {
        return matrix;
    }

}
