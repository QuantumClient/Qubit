#version 330 core

uniform sampler2D u_texture;

in vec4 v_color;
in vec2 v_texCoord;

const float smoothing = 1.0/16.0;
out vec4 color;
void main() {
    float distance = texture(u_texture, v_texCoord).a;
    float alpha = smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);
    color = vec4(v_color.rgb, v_color.a * alpha);
}