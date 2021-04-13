package org.quantumclient.qubit.settings;

import org.jetbrains.annotations.Nullable;

public abstract class Setting<T> {

    protected String name;

    @Nullable
    protected String description;

    protected T value;

    //for number settings can't think of a better way right now

    public String getName() {
        return name;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public T getValue() {
        return (T) value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
