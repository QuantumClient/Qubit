package org.quantumclient.qubit.settings;

public class CheckSetting extends Setting<Boolean> {

    /**
     * Used for settings as booleans
     * @param name what the settings is called
     * @param description what will this setting change
     * @param value what is its default vaule
     */
    public CheckSetting(String name, String description, boolean value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public CheckSetting(String name, boolean value) {
        this(name, null, value);
    }

}
