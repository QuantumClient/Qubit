package org.quantumclient.qubit.settings;

public class CheckSetting extends Setting<Boolean> {

    public CheckSetting(String name, String description, boolean value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public CheckSetting(String name, boolean value) {
        this(name, null, value);
    }

}
