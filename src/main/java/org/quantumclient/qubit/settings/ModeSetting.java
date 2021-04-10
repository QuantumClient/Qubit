package org.quantumclient.qubit.settings;

public class ModeSetting extends Setting<String> {

    private final String[] modes;

    public ModeSetting(String name, String description, String value, String[] modes) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.modes = modes;
    }

    public ModeSetting(String name, String vaule, String[] modes) {
        this(name, null, vaule, modes);
    }


    public String[] getModes() {
        return modes;
    }
}
