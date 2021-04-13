package org.quantumclient.qubit.settings.numbers;

import org.quantumclient.qubit.settings.Setting;

public class DoubleSetting extends Setting<Double> {

    protected double min;
    protected double max;
    protected double inc;
    protected int dec;

    public DoubleSetting(String name, String description, double value, double min, double max, double inc, int dec) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.dec = dec;
    }

    public DoubleSetting(String name, double value, double min, double max, double inc, int dec) {
        this(name, null, value, min, max, inc, dec);
    }


    public double getInc() {
        return inc;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public int getDec() {
        return dec;
    }

}
