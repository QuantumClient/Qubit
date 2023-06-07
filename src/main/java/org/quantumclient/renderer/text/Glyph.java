package org.quantumclient.renderer.text;

import net.minecraft.client.texture.NativeImageBackedTexture;

public record Glyph(int width, int height, NativeImageBackedTexture texture) {

    public int getId() {
        return texture.getGlId();
    }
}
