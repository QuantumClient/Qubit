package org.quantumclient.renderer.text;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec2f;

import java.awt.*;

public class FontRenderer {

    private static final char formatingBase = '\u00a7';

    private GlyphPage glyphPage;
    private int[] colorCodes = new int[32];

    public FontRenderer(GlyphPage glyphPage) {
        this.glyphPage = glyphPage;
        for (int i = 0; i < 32; ++i) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;

            if (i == 6) {
                k += 85;
            }

            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCodes[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }
    }

    public FontRenderer(Font font) {
        this(new GlyphPage(font));
    }


    public void drawString(MatrixStack matrix, String s, float x, float y, boolean shadow, Color color) {
        if (s.isEmpty()) return;
        float drawX = x*4f;
        float drawY = y*4f;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = Tessellator.getInstance().getBuffer();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        matrix.push();
        matrix.scale(0.25F, 0.25F, 1);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == formatingBase) {
                int colorCode = "0123456789abcdefklmnor".indexOf(String.valueOf(s.charAt(i + 1)).toLowerCase().charAt(0));
                color = formatColor(colorCodes[colorCode]);
            } else {

                drawX += glyphPage.drawChar(matrix, c, drawX, drawY, color, builder, tessellator);
            }
        }
        matrix.pop();

        RenderSystem.disableBlend();
    }

    private Color formatColor(int color) {
        float red = (float) (color >> 16 & 255) / 255.0F;
        float blue = (float) (color >> 8 & 255) / 255.0F;
        float green = (float) (color & 255) / 255.0F;
        return new Color(red, blue, green, 1);
    }

}