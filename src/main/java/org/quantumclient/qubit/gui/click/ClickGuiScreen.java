package org.quantumclient.qubit.gui.click;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.utils.FontUtils;
import org.quantumclient.qubit.utils.RenderUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {

    private final List<Frame> frames = new ArrayList<>();

    @Nullable
    private static String description;

    public ClickGuiScreen() {
        super(Text.of("ClickGui"));
    }

    @Override
    public void init() {
        super.init();
        int i = 0;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, 20 + (i * 110), 30));
            i++;
        }
        Qubit.getConfigManger().loadGui(this);
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        super.render(matrix, mouseX, mouseY, delta);
        super.renderBackground(matrix);
        for (Frame frame : frames) {
            frame.render(matrix, mouseX, mouseY);
        }
        if (description != null && description != "") {
            RenderUtils.drawRect(mouseX + 10, mouseY - 2, mouseX + 15 + FontUtils.getWidth(description), mouseY + client.textRenderer.fontHeight, new Color(63, 91, 115));
            RenderUtils.drawRectOutLine(mouseX + 10, mouseY - 2, mouseX + 15 + FontUtils.getWidth(description), mouseY + client.textRenderer.fontHeight, new Color(100, 141, 184));
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
            frame.keyPressed(keyCode);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        super.onClose();
        Qubit.getConfigManger().saveGui(this);
    }

    static void setDescription(String s) {
        description = s;
    }

    public List<Frame> getFrames() {
        return frames;
    }
}
