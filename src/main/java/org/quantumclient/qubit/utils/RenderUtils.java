package org.quantumclient.qubit.utils;


import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class RenderUtils {

    //could be replaced by DrawableHelper but I like it

    public static void drawRect(float x, float y, float w, float h, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        begin();
        bufferbuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
        bufferbuilder.vertex(x, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(w, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(w, y, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(x, y, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        tessellator.draw();
        end();
    }

    public static void drawRectOutLine(float x, float y, float w, float h, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        begin();
        bufferbuilder.begin(GL11.GL_LINE_LOOP, VertexFormats.POSITION_COLOR);
        bufferbuilder.vertex(x, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(w, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(w, y, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(x, y, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        tessellator.draw();
        end();
    }

    private static void begin() {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.lineWidth(2);
    }

    public static void end() {
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

}
