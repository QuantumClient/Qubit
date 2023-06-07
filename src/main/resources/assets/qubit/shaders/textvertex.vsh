#version 330 core
uniform mat4 u_projTrans;

in vec4 a_position;
in vec2 a_texCoord0;
in vec4 a_color;

out vec4 v_color;
out vec2 v_texCoord;

void main() {
    gl_Position = u_projTrans * a_position;
    v_texCoord = a_texCoord0;
    v_color = a_color;
}