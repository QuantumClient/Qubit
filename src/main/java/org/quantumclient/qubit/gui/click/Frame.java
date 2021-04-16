package org.quantumclient.qubit.gui.click;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.Setting;
import org.quantumclient.qubit.settings.numbers.DoubleSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.RenderUtils;
import org.quantumclient.qubit.utils.Wrapper;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Frame implements Wrapper {

    private int x;
    private int y;
    private int oldMX;
    private int oldMY;
    private final Category category;
    private final int width = 90;
    private final int height = 14;
    private int key;
    private int clickedButton;
    private boolean dragging;
    private boolean open = true;

    public Frame(Category category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
    }

    void render(MatrixStack matrix, int mouseX, int mouseY) {
        int m = 1;
        RenderUtils.drawRect(x - 5 , y, x + width + 5, y + height, new Color(63, 91, 115));
        RenderUtils.drawRectOutLine(x - 5, y, x + width + 5,  y + height, new Color(100, 141, 184));

        mc.textRenderer.draw(matrix, StringUtils.capitalize(category.name().toLowerCase()), (x + ((x + width) - x) / 2 - (mc.textRenderer.getWidth(new LiteralText(category.name().toLowerCase()))) / 2), y + 3, -1);

        if (open) for (Module module : Qubit.getModuleManger().getModulesInCat(category)) {


            RenderUtils.drawRect(x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
            if (module.isToggled())
                RenderUtils.drawRect(x, y + m * height + 2, x + width, y + height + m * height - 2, new Color(63, 91, 115));


            int o = m;
            //RenderUtils.drawRect(x, y + m * height, x + width, y + height + m * height, (module.isToggled()) ? new Color(63, 91, 115, 150) : new Color(0, 0, 0, 150));
            mc.textRenderer.draw(matrix, StringUtils.capitalize(module.getName()), x + 2, y + 3 + height * m, -1);
            if(mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                if (clickedButton == 0) module.toggle();
                if (clickedButton == 1) module.setOpen(!module.isOpen());
                ClickGuiScreen.setDescription(module.getDescription());
            }
            m++;
            if (module.isOpen() && module.hasSetting()) for (Setting setting : module.getSettingList()) {
                if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                    ClickGuiScreen.setDescription(setting.getDescription());
                }
                if (setting instanceof CheckSetting) {
                    CheckSetting checkSetting = (CheckSetting) setting;
                    RenderUtils.drawRect(x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                    if (checkSetting.getValue())
                        RenderUtils.drawRect(x, y + m * height + 2, x + width, y + height + m * height - 2, new Color(63, 91, 115));

                    mc.textRenderer.draw(matrix, StringUtils.capitalize(setting.getName()), x + 2, y + 3 + height * m, -1);
                    if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                        if (clickedButton == 0) checkSetting.setValue(!checkSetting.getValue());
                    }
                }
                if (setting instanceof ModeSetting) {
                    ModeSetting modeSetting = (ModeSetting) setting;
                    RenderUtils.drawRect(x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                    mc.textRenderer.draw(matrix, StringUtils.capitalize(setting.getName()) + ": " + setting.getValue(), x + 2, y + 3 + height * m, -1);
                    if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                        if (clickedButton == 0) modeSetting.setValue(modeSetting.nextMode());
                    }
                }
                if (setting instanceof FloatSetting) {
                    FloatSetting floatSetting = (FloatSetting) setting;
                    float newWidth = width * (floatSetting.getValue() - floatSetting.getMin()) / (floatSetting.getMax() - floatSetting.getMin());
                    RenderUtils.drawRect(x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                    RenderUtils.drawRect(x, y + m * height + 2, x + newWidth , y + height + m * height - 2, new Color(63, 91, 115));
                    mc.textRenderer.draw(matrix, StringUtils.capitalize(setting.getName()) + ": " + setting.getValue(), x + 2, y + 3 + height * m, -1);
                    if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                        if (dragging) {
                            float diff = mouseX - x;
                            if (diff == 0) floatSetting.setValue(floatSetting.getMin());
                            else {
                                BigDecimal bigDecimal = new BigDecimal(diff / width * (floatSetting.getMax()) - floatSetting.getMin() + floatSetting.getMin());
                                float newValue = bigDecimal.setScale(floatSetting.getDec(), RoundingMode.HALF_UP).floatValue();
                                if (newValue >= floatSetting.getMin() && newValue <= floatSetting.getMax()) floatSetting.setValue(newValue);
                            }
                        }
                    }
                }
                if (setting instanceof DoubleSetting) {
                    DoubleSetting doubleSetting = (DoubleSetting) setting;
                    double newWidth = width * (doubleSetting.getValue() - doubleSetting.getMin()) / (doubleSetting.getMax() - doubleSetting.getMin());
                    RenderUtils.drawRect(x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                    RenderUtils.drawRect(x, y + m * height + 2, (float) (x + newWidth), y + height + m * height - 2, new Color(63, 91, 115));
                    mc.textRenderer.draw(matrix, StringUtils.capitalize(setting.getName()) + ": " + setting.getValue(), x + 2, y + 3 + height * m, -1);
                    if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                        if (dragging) {
                            double diff = mouseX - x;
                            if (diff == 0) doubleSetting.setValue(doubleSetting.getMin());
                            else {
                                BigDecimal bigDecimal = new BigDecimal(diff / width * (doubleSetting.getMax()) - doubleSetting.getMin() + doubleSetting.getMin());
                                double newValue = bigDecimal.setScale(doubleSetting.getDec(), RoundingMode.HALF_UP).doubleValue();
                                if (newValue >= doubleSetting.getMin() && newValue <= doubleSetting.getMax()) doubleSetting.setValue(newValue);
                            }
                        }
                    }
                }
                m++;
            }
            if (module.isOpen()) {
                RenderUtils.drawRect(x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                String string = (module.isBinding()) ? "..." : (module.getBind() == -1) ? "NONE" : InputUtil.fromKeyCode(module.getBind() , -1).getLocalizedText().getString();
                mc.textRenderer.draw(matrix, StringUtils.capitalize("Bind: " + string), x + 2, y + 3 + height * m, -1);
                if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                    if (clickedButton == 0) module.setBinding(!module.isBinding());
                    if (module.isBinding() && key != -1) {
                        if (key == GLFW.GLFW_KEY_BACKSPACE)
                            module.setBind(-1);
                        else
                            module.setBind(key);
                        module.setBinding(false);
                    }
                }
                m++;
            }
            RenderUtils.drawRectOutLine(x, y + o * height, x + width, y + m * height, new Color(100, 141, 184));

        }

        if(mouseX >= x -5 && mouseY >= y && mouseX <= x + width + 5 && mouseY <= y + height) {
            if (clickedButton == 1) open = !open;
            if (dragging) {
                x = mouseX - (oldMX - x);
                y = mouseY - (oldMY - y);
            }
            oldMX = mouseX;
            oldMY = mouseY;
        }
        clickedButton = -1;
        key = -1;
    }

    void mouseClicked(double mouseX, double mouseY, int button) {
        clickedButton = button;
        if (button == 0) dragging = true;
    }

    void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        this.dragging = false;
    }

    void keyPressed(int key) {
        this.key = key;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Category getCategory() {
        return category;
    }
}
