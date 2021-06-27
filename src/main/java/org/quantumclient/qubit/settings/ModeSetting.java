package org.quantumclient.qubit.settings;

public class ModeSetting extends Setting<String> {

    private final String[] modes;

    /**
     * Uses for settings as modes with strings
     * @param name what the settings is called
     * @param description what will this setting change
     * @param value what is its default value
     * @param modes the other value options
     */
    public ModeSetting(String name, String description, String value, String[] modes) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.modes = modes;
    }

    public ModeSetting(String name, String vaule, String[] modes) {
        this(name, null, vaule, modes);
    }

    public String nextMode() {
        int i = 0;
        String returnString;
        for (String string : modes) {
            if (string.equals(getValue())) {
                break;
            }
            i++;
        }
        if (i + 1 == modes.length) returnString = modes[0];
        else returnString = modes[i + 1];
        return returnString;
    }

}
