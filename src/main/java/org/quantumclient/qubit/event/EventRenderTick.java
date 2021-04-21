package org.quantumclient.qubit.event;

import org.quantumclient.energy.Event;

public class EventRenderTick extends Event {

    private float lastFrameDuration;

    public EventRenderTick(Float lastFrameDuration) {
        this.lastFrameDuration = lastFrameDuration;
    }

    public float getLastFrameDuration() {
        return lastFrameDuration;
    }

    public void setLastFrameDuration(float lastFrameDuration) {
        this.lastFrameDuration = lastFrameDuration;
    }

}
