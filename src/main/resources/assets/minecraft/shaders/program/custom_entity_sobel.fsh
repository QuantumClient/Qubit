#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

vec4 color;
vec4 current;
vec4 currentColor;

out vec4 fragColor;

void main() {
    float radius = 1;
    vec4 color = vec4(0, 0, 0, 0);

    vec4 current = texture(DiffuseSampler, texCoord);
    if (current.a == 0) {
        for (float x = -radius; x <= radius; x++) {
            for (float y = -radius; y <= radius; y++) {
                vec4 currentColor = texture(DiffuseSampler, texCoord + vec2(oneTexel.x * x, oneTexel.y * y));
                if (currentColor.a != 0) {
                    color = currentColor;
                }
            }
        }
        fragColor = color;
    } else {
        fragColor = vec4(current.rgb, 0);
    }
}