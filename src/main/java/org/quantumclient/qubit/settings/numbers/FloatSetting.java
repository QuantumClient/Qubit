package org.quantumclient.qubit.settings.numbers;

import org.quantumclient.qubit.settings.Setting;

public class FloatSetting extends Setting<Float> {

    protected float min;
    protected float max;
    protected float inc;
    protected int dec;

    public FloatSetting(String name, String description, float value, float min, float max, float inc, int dec) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.dec = dec;
    }

    public FloatSetting(String name, float vaule, float min, float max, float inc, int dec) {
        this(name, null, vaule, min, max, inc, dec);
    }

    public float getInc() {
        return inc;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public int getDec() {
        return dec;
    }
}
