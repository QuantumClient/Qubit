package org.quantumclient.qubit.event;

import org.quantumclient.energy.Event;

public class EventKeyPress extends Event {

    private final int key;
    private final int action;
    private final int modifiers;

    public EventKeyPress(int key, int action, int modifiers) {
        this.key = key;
        this.action = action;
        this.modifiers = modifiers;
    }

    public int getKey() {
        return key;
    }

    public int getAction() {
        return action;
    }

    public int getModifiers() {
        return modifiers;
    }
}
