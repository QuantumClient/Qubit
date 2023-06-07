package org.quantumclient.renderer;

import org.lwjgl.BufferUtils;
import org.quantumclient.renderer.maths.Vector3f;
import org.quantumclient.renderer.shader.ShaderProgram;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexDrawer {

    private static final int FLOAT_SIZE = 4;

    private int vaoID;
    private int vboID;
    private int eboID;
    private boolean texture;

    private float[] vertices = new float[1000];
    private int[] elements = new int[1000];
    private int index = 0;
    private int indexE = 0;
    private int size = 0;

    private ShaderProgram shaderProgram;

    private DrawMode drawMode;

    public VertexDrawer(DrawMode drawMode, Attribute... attributes) {
        this.drawMode = drawMode;

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();

        eboID = glGenBuffers();

        var size = 0;
        for (Attribute attribute : attributes) {
            if (attribute.equals(Attribute.TEXT)) texture = true;
            size += attribute.size * FLOAT_SIZE;
        }
        var offset = 0;
        for (Attribute attribute : attributes) {
            glVertexAttribPointer(attribute.id, attribute.size, GL_FLOAT, false, size, offset);
            glEnableVertexAttribArray(attribute.id);
            offset += attribute.size * FLOAT_SIZE;
        }
        //var shaderName = "qubit/shaders/color";
        //if (texture) {
        //    shaderName += "_text";
        //}
        //shaderProgram = new ShaderProgram(shaderName + ".vsh", shaderName + "fsh");
        shaderProgram = new ShaderProgram("qubit/shaders/color.vsh", "qubit/shaders/color.fsh");

    }

    private void addFloat(float f) {
        vertices[index] = f;
        index++;
    }

    public void addElm(int i) {
        elements[indexE] = i;
        indexE++;
    }

    public VertexDrawer vertex(float x, float y, float z) {
        addFloat(x);
        addFloat(y);
        addFloat(z);
        size += 1;
        return this;
    }

    public VertexDrawer color(float r, float g, float b, float a) {
        addFloat(r);
        addFloat(g);
        addFloat(b);
        addFloat(a);
        return this;
    }

    public VertexDrawer color(Color color) {
        return this.color(color.getRed() / 255, color.getGreen() / 255, color.getBlue() / 255, color.getAlpha() / 255);
    }

    public void end() {
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elements.length);
        elementBuffer.put(elements).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    }


    public void draw() {
        shaderProgram.bind();
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glDrawElements(drawMode.mode, size, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        shaderProgram.unbind();
        vertices = new float[1000];
        elements = new int[1000];
        index = 0;
        indexE = 0;
    }



}
