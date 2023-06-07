package org.quantumclient.qubit.gui.click;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL20;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.utils.FontUtils;
import org.quantumclient.qubit.utils.RenderUtils;
import org.quantumclient.renderer.shader.ShaderProgram;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

public class ClickGuiScreen extends Screen {

    private final List<Frame> frames = new ArrayList<>();
    ShaderProgram shaderProgram;

    @Nullable
    private static String description;

    public ClickGuiScreen() {
        super(Text.of("ClickGui"));
    }

    @Override
    public void init() {
        super.init();
        shaderProgram = new ShaderProgram("qubit/shaders/blur.vsh", "qubit/shaders/blur.fsh");
        int i = 0;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, 20 + (i * 110), 30));
            i++;
        }

        Qubit.getConfigManger().loadGui(this);
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {

        super.renderBackground(matrix);
        shaderProgram.bind();
        glUniform4f(glGetUniformLocation(shaderProgram.getProgramID(), "uColor"),
                200/255f-0.5f,
                10/255f-0.5f,
                123/255f-0.5f,
                255/255f);
        GL20.glUniform1f(GL20.glGetUniformLocation(shaderProgram.getProgramID(), "uBlurSize"), 52);
        GL20.glUniform2f(GL20.glGetUniformLocation(shaderProgram.getProgramID(), "uTexelSize"), 1.0f, 1.0f);
        GL20.glUniform4f(GL20.glGetUniformLocation(shaderProgram.getProgramID(), "uCornerRadii"), width, 300, 1000, 1000);

        super.render(matrix, mouseX, mouseY, delta);
        for (Frame frame : frames) {
            frame.render(matrix, mouseX, mouseY);
        }
        if (description != null && !StringUtils.isEmpty(description)) {
            RenderUtils.drawRect(matrix,mouseX + 10, mouseY - 2, mouseX + 15 + FontUtils.getWidth(description), mouseY + client.textRenderer.fontHeight, new Color(63, 91, 115));
            RenderUtils.drawRectOutLine(matrix,mouseX + 10, mouseY - 2, mouseX + 15 + FontUtils.getWidth(description), mouseY + client.textRenderer.fontHeight, new Color(100, 141, 184));
            FontUtils.drawText(matrix, description, mouseX + 12, mouseY, false, Color.WHITE);
        }

        description = null;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Frame frame : frames) {
            frame.keyPressed(keyCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        super.onClose();
        Qubit.getConfigManger().saveGui(this);
        //if (client.gameRenderer.getShader() != null) {
        //    client.gameRenderer.getShader().close();
        //}
    }

    static void setDescription(String s) {
        description = s;
    }

    public List<Frame> getFrames() {
        return frames;
    }
}
