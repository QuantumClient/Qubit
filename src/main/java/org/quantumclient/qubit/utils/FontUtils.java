package org.quantumclient.qubit.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.renderer.text.FontRenderer;
import org.quantumclient.renderer.text.GlyphPage;

import java.awt.*;

public class FontUtils implements Wrapper {

    private static FontRenderer tahoma;
    private static final TextRenderer tr = mc.textRenderer;
    private static boolean custom;

    public static void drawText(MatrixStack matrix, String string, float x, float y, boolean shawdow, Color color) {
        if (tahoma == null) tahoma = new FontRenderer(new GlyphPage(new Font("Tahoma", Font.PLAIN, 1000), 1000));
        custom = Qubit.getModuleManger().isModuleEnabled(org.quantumclient.qubit.module.client.Font.class);
        if (custom) {
            tahoma.drawString(matrix, string, x, y - 3, shawdow, color);
        } else {
            if (shawdow) {
                tr.drawWithShadow(matrix, string, x, y, color.getRGB());
            } else {
                tr.draw(matrix, string, x, y, color.getRGB());
            }
        }
    }

    public static void init() {
        if (tahoma == null) {
            tahoma = new FontRenderer(new GlyphPage(new Font("Tahoma", Font.PLAIN, 128), 128));
        }

    }

    public static float getWidth(String s) {
        custom = Qubit.getModuleManger().isModuleEnabled(org.quantumclient.qubit.module.client.Font.class);
        if (custom) {
            return tahoma.getWidth(s);
        } else {
            return tr.getWidth(s);
        }
    }

}
