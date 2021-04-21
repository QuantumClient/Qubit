package org.quantumclient.qubit.settings;

import org.jetbrains.annotations.Nullable;

public abstract class Setting<T> {

    protected String name;

    @Nullable
    protected String description;

    protected T value;

    public String getName() {
        return name;
    }

    public @Nullable String getDescription() {
        return description;
    }

    /**
     *
     * @return returns the current value of the setting
     */
    public T getValue() {
        return (T) value;
    }

    /**
     *
     * @param value sets the vaule of the setting
     */
    public void setValue(T value) {
        this.value = value;
    }

}
