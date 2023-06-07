package org.quantumclient.renderer.shader;

import org.lwjgl.system.MemoryUtil;
import org.quantumclient.renderer.maths.Matrix4f;
import org.quantumclient.renderer.maths.Vector2f;
import org.quantumclient.renderer.maths.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL32.*;

public class ShaderProgram {
    
    private String vertexFile;
    private String fragmentFile;
    private int vertexID;
    private int fragmentID;
    private int programID;

    public int getProgramID() {
        return programID;
    }

    public ShaderProgram(String vertexPath, String fragmentPath) {
        this.vertexFile = loadShader(vertexPath);
        this.fragmentFile = loadShader(fragmentPath);
        create();
    }

    public void create() {
        programID = glCreateProgram();
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertexID, vertexFile);
        glCompileShader(vertexID);

        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) != GL_TRUE) {
            System.err.println("Vertex Shader: " + glGetShaderInfoLog(vertexID));
            return;
        }


        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fragmentID, fragmentFile);
        glCompileShader(fragmentID);

        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) != GL_TRUE) {
            System.err.println("Fragment Shader: " + glGetShaderInfoLog(fragmentID));
            return;
        }

        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);

        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) != GL_TRUE) {
            System.err.println("Program Linking: " + glGetProgramInfoLog(programID));
            return;
        }

        //glValidateProgram(programID);
        //if (glGetProgrami(programID, GL_VALIDATE_STATUS) != GL_TRUE) {
        //    System.err.println("Program Validation: " + glGetProgramInfoLog(programID));
        //    return;
        //}

    }

    public int getUniformLocation(String name) {
        return glGetUniformLocation(programID, name);
    }

    public void setUniform(String name, int v0) {
        glUniform1i(getUniformLocation(name), v0);
    }

    public void setUniform(String name, float v0) {
        glUniform1f(getUniformLocation(name), v0);
    }

    public void setUniform(String name, boolean value) {
        glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    public void setUniform(String name, Vector2f value) {
        glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }

    public void setUniform(String name, Vector3f value) {
        glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String name, Matrix4f value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        matrix.put(value.getAll()).flip();
        glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }

    public void bindAttrib(int index, String name) {
        glBindAttribLocation(programID, index, name);
    }

    public void bind() {
        glUseProgram(programID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void destroy() {
        glDetachShader(programID, vertexID);
        glDetachShader(programID, fragmentID);
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
        glDeleteProgram(programID);
    }

    protected String loadShader(String path) {

        StringBuilder result = new StringBuilder();

        try {
            InputStream in = getClass().getResourceAsStream("/assets/" + path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Can not find: " + path);
        }
        return result.toString();
    }



}
