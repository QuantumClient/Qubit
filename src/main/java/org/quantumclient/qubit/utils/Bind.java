package org.quantumclient.qubit.utils;

import net.minecraft.client.util.InputUtil;

public class Bind {

    private Type type;

    private int key;

    private int modifier;

    public Bind() {
    }

    public Bind(int key, int modifier, Type type) {
        this.key = key;
        this.modifier = modifier;
        this.type = type;
    }

    public Bind(int key, int modifiers) {
       this(key, modifiers, Type.TOGGLE);
    }

    public Bind(int key) {
        this(key, 0);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Type getType() {
        return type;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public boolean pressMatches(int key, int modifier) {
        return (getKey() == key && (getModifier() == 0 || getModifier() == modifier));
    }

    @Override
    public String toString() {
        if (getKey() == -1) {
            return "NONE";
        } else {
            if (getModifier() != 0) {
                return InputUtil.fromKeyCode(getKey(), -1).getLocalizedText().getString() + ", and modifer";
            } else {
                return InputUtil.fromKeyCode(getKey(), -1).getLocalizedText().getString();
            }
        }
    }

    public enum Type {
        TOGGLE,
        HOLD
    }
}
