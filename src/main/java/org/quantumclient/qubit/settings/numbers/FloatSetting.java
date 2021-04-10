package org.quantumclient.qubit.settings.numbers;

import org.quantumclient.qubit.settings.Setting;

public class FloatSetting extends Setting<Float> {

    public FloatSetting(String name, String description, float vaule, float min, float max, float inc, int dec) {
        this.name = name;
        this.description = description;
        this.value = vaule;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.dec = dec;
    }

    public FloatSetting(String name, float vaule, float min, float max, float inc, int dec) {
        this(name, null, vaule, min, max, inc, dec);
    }

}
