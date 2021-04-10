package org.quantumclient.qubit.settings;

import org.jetbrains.annotations.Nullable;

public abstract class Setting<T> {

    protected String name;

    @Nullable
    protected String description;

    protected T value;

    //for number settings can't think of a better way right now
    protected T min;
    protected T max;
    protected T inc;
    protected int dec;

    public String getName() {
        return name;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getInc() {
        return inc;
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }

    public int getDec() {
        return dec;
    }
}
