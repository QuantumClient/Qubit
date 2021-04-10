package org.quantumclient.qubit.settings.numbers;

import org.quantumclient.qubit.settings.Setting;

public class DoubleSetting extends Setting<Double> {

    public DoubleSetting(String name, String description, double vaule, double min, double max, double inc, int dec) {
        this.name = name;
        this.description = description;
        this.value = vaule;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.dec = dec;
    }

    public DoubleSetting(String name, double vaule, double min, double max, double inc, int dec) {
        this(name, null, vaule, min, max, inc, dec);
    }

}
