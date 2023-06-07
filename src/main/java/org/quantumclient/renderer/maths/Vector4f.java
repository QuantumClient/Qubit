package org.quantumclient.renderer.maths;

public class Vector4f {
    private final float x;
    private final float y;
    private final float z;
    private final float w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + "," + this.z + ")";
    }
}

