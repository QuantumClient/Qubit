package org.quantumclient.qubit.event;

import net.minecraft.entity.Entity;
import org.quantumclient.energy.Event;

public class EventEntityRender extends Event {

    private final Entity entity;

    public EventEntityRender(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}
