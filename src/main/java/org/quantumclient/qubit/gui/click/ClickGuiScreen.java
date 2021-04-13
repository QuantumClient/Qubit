package org.quantumclient.qubit.gui.click;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.quantumclient.qubit.module.Category;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {

    private List<Frame> frames = new ArrayList<>();

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
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        super.render(matrix, mouseX, mouseY, delta);
        //super.renderBackground(matrix);
        for (Frame frame : frames) {
            frame.render(matrix, mouseX, mouseY);
        }
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
}
