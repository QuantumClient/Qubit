package org.quantumclient.renderer.maths;

public class Vector3f {
    private final float x;
    private final float y;
    private final float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public String toString() {
        return "(" + this.x + "," + this.y + "," + this.z + ")";
    }
}

