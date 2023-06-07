package org.quantumclient.renderer;

import org.lwjgl.opengl.GL11;

public enum DrawMode {
    QUADS(GL11.GL_TRIANGLES, 4),
    LINE(GL11.GL_LINES, 2),
    LINELOOP(GL11.GL_LINE_LOOP, 4);

    public final int mode;
    public final int count;

    DrawMode(int mode, int count) {
        this.mode = mode;
        this.count = count;
    }

}
