package org.quantumclient.renderer.text;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.quantumclient.renderer.shader.ShaderProgram;

import java.io.IOException;
import java.util.Map;

public class GlyphPage {

    private final Font font;
    private int height;
    private ShaderProgram shaderProgram;

    private final Map<Character, Glyph> glyphs = new HashMap<>();

    public GlyphPage(Font font) {
        this.font = font;
        make();
        shaderProgram = new ShaderProgram("qubit/shaders/textvertex.vsh", "qubit/shaders/textfrag.fsh");

    }

    private void make() {
        int imageHeight = 0;

        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c);
            if (charImage == null) {
                continue;
            }
            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            imageHeight = Math.max(imageHeight, charImage.getHeight());
            NativeImageBackedTexture texture;
            try {
                texture = makeTexture(charImage);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            Glyph glyph = new Glyph(charWidth, charHeight, texture);
            glyphs.put(c, glyph);
        }
        height = imageHeight;


    }

    private BufferedImage createCharImage(Font font, char c) {
        var image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        FontMetrics metrics = g.getFontMetrics(font);
        g.dispose();

        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        if (charWidth == 0) {
            return null;
        }

        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setFont(font);
        g.setPaint(Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    public NativeImageBackedTexture makeTexture(BufferedImage image) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        byte[] bytes = bos.toByteArray();
        ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length).put(bytes);
        buffer.flip();

        return new NativeImageBackedTexture(NativeImage.read(buffer));
    }

    protected float drawChar(MatrixStack matrixStack, char ch, float x, float y, Color c, BufferBuilder builder, Tessellator tessellator) {
        Glyph g = glyphs.get(ch);

        RenderSystem.enableTexture();
        Matrix4f matrix4f = matrixStack.peek().getModel();
        RenderSystem.setShaderTexture(0, g.getId());
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        shaderProgram.bind();
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        builder.vertex(matrix4f, x, y, 0F).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).texture(0, 0).next();
        builder.vertex(matrix4f, x, y + g.height(), 0F).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).texture(0, 1).next();
        builder.vertex(matrix4f, x + g.width(), y + g.height(), 0F).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).texture(1, 1).next();
        builder.vertex(matrix4f, x + g.width(), y, 0F).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).texture(1, 0).next();
        tessellator.draw();
        shaderProgram.unbind();

        return g.width();
    }


}
