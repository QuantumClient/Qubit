package org.quantumclient.qubit.settings.numbers;

import org.quantumclient.qubit.settings.Setting;

public class FloatSetting extends Setting<Float> {

    protected float min;
    protected float max;
    protected int dec;

    /**
     * Used for number settings as a float
     * @param name what the settings is called
     * @param description what this settings changes
     * @param value what the default vaule is
     * @param min the minimum vaule the setting can be
     * @param max the maximum vaule the setting can be
     * @param dec how many decimals the settings has
     */
    public FloatSetting(String name, String description, float value, float min, float max, int dec) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.min = min;
        this.max = max;
        this.dec = dec;
    }

    public FloatSetting(String name, float vaule, float min, float max, int dec) {
        this(name, null, vaule, min, max, dec);
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
