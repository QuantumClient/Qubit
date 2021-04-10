package org.quantumclient.qubit.settings;

public class CheckSetting extends Setting<Boolean> {

    public CheckSetting(String name, String description, boolean vaule) {
        this.name = name;
        this.description = description;
        this.value = vaule;
    }

    public CheckSetting(String name, boolean value) {
        this(name, null, value);
    }

}
