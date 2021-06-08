package org.quantumclient.qubit.utils;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class RenderUtils {

    //could be replaced by DrawableHelper but I like it

    public static void drawRect(MatrixStack matrixStack, float x, float y, float w, float h, Color color) {
        Matrix4f matrix = matrixStack.peek().getModel();
        matrixStack.push();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        begin();
        bufferbuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferbuilder.vertex(matrix, x, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(matrix, w, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(matrix, w, y, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(matrix, x, y, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        //tessellator.draw();
        bufferbuilder.end();
        BufferRenderer.draw(bufferbuilder);
        matrixStack.pop();
        end();
    }

    public static void drawRectOutLine(MatrixStack matrixStack, float x, float y, float w, float h, Color color) {
        Matrix4f matrix = matrixStack.peek().getModel();
        matrixStack.push();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        begin();
        bufferbuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        bufferbuilder.vertex(matrix, x, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(matrix, w, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(matrix, w, y, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(matrix, x, y, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.vertex(matrix, x, h, 0).color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f).next();
        bufferbuilder.end();
        BufferRenderer.draw(bufferbuilder);
        matrixStack.pop();
        end();
    }

    private static void begin() {
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.lineWidth(2);
        //GlStateManager.shadeModel(GL11.GL_SMOOTH);
    }

    public static void end() {
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

}
