package org.quantumclient.renderer;

public enum Attribute {
    POS(3, 0),
    COLOR(4, 1),
    TEXT(2, 2);

    public int size;
    public int id;

    Attribute(int size, int id) {
        this.size = size;
        this.id = id;
    }
}
