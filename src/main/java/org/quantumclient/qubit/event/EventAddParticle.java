package org.quantumclient.qubit.event;

import net.minecraft.particle.ParticleEffect;
import org.quantumclient.energy.Event;

public class EventAddParticle extends Event {

    private ParticleEffect particleEffect;

    public EventAddParticle(ParticleEffect particleEffect) {
        this.particleEffect = particleEffect;
    }

    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }

}
