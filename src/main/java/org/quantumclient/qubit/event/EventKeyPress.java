package org.quantumclient.qubit.event;

import org.quantumclient.energy.Event;

public class EventKeyPress extends Event {

    private final int key;
    private final int action;

    public EventKeyPress(int key, int action) {
        this.key = key;
        this.action = action;
    }

    public int getKey() {
        return key;
    }

    public int getAction() {
        return action;
    }

}
