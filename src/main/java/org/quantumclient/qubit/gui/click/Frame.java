package org.quantumclient.qubit.gui.click;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.Setting;
import org.quantumclient.qubit.settings.numbers.DoubleSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.FontUtils;
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
    private int modifiers;
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
        RenderUtils.drawRect(matrix, x - 5 , y, x + width + 5, y + height, new Color(63, 91, 115));
        RenderUtils.drawRectOutLine(matrix, x - 5, y, x + width + 5,  y + height, new Color(100, 141, 184));

     //   mc.textRenderer.draw(matrix, StringUtils.capitalize(category.name().toLowerCase()), (x + ((x + width) - x) / 2 - (mc.textRenderer.getWidth(new LiteralText(category.name().toLowerCase()))) / 2), y + 3, -1);
        FontUtils.drawText(matrix,StringUtils.capitalize(category.name().toLowerCase()), (x + ((x + width) - x) / 2 - (mc.textRenderer.getWidth(new LiteralText(category.name().toLowerCase()))) / 2), y + 3, true, Color.white);
        if (open) for (Module module : Qubit.getModuleManger().getModulesInCat(category)) {


            RenderUtils.drawRect(matrix, x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
            if (module.isToggled())
                RenderUtils.drawRect(matrix, x, y + m * height + 2, x + width, y + height + m * height - 2, new Color(63, 91, 115));


            int o = m;
            //RenderUtils.drawRect(x, y + m * height, x + width, y + height + m * height, (module.isToggled()) ? new Color(63, 91, 115, 150) : new Color(0, 0, 0, 150));
            FontUtils.drawText(matrix, StringUtils.capitalize(module.getName()), x + 2, y + 3 + height * m, false, Color.WHITE);
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
                    RenderUtils.drawRect(matrix, x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                    if (checkSetting.getValue())
                        RenderUtils.drawRect(matrix, x, y + m * height + 2, x + width, y + height + m * height - 2, new Color(63, 91, 115));

                    FontUtils.drawText(matrix, StringUtils.capitalize(setting.getName()), x + 2, y + 3 + height * m, false, Color.WHITE);
                    if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                        if (clickedButton == 0) checkSetting.setValue(!checkSetting.getValue());
                    }
                }
                if (setting instanceof ModeSetting) {
                    ModeSetting modeSetting = (ModeSetting) setting;
                    RenderUtils.drawRect(matrix, x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                    FontUtils.drawText(matrix, StringUtils.capitalize(setting.getName()) + ": " + setting.getValue(), x + 2, y + 3 + height * m, false, Color.WHITE);
                    if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                        if (clickedButton == 0) modeSetting.setValue(modeSetting.nextMode());
                    }
                }
                if (setting instanceof FloatSetting) {
                    FloatSetting floatSetting = (FloatSetting) setting;
                    float newWidth = width * (floatSetting.getValue() - floatSetting.getMin()) / (floatSetting.getMax() - floatSetting.getMin());
                    RenderUtils.drawRect(matrix, x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                    RenderUtils.drawRect(matrix, x, y + m * height + 2, x + newWidth , y + height + m * height - 2, new Color(63, 91, 115));
                    FontUtils.drawText(matrix, StringUtils.capitalize(setting.getName()) + ": " + setting.getValue(), x + 2, y + 3 + height * m, false, Color.WHITE);
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
                    RenderUtils.drawRect(matrix, x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                    RenderUtils.drawRect(matrix, x, y + m * height + 2, (float) (x + newWidth), y + height + m * height - 2, new Color(63, 91, 115));
                    FontUtils.drawText(matrix, StringUtils.capitalize(setting.getName()) + ": " + setting.getValue(), x + 2, y + 3 + height * m, false, Color.WHITE);
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
                RenderUtils.drawRect(matrix, x, y + m * height, x + width, y + height + m * height, new Color(0, 0, 0, 150));
                String string = (module.isBinding()) ? "..." : module.getBind().toString();
                FontUtils.drawText(matrix, StringUtils.capitalize("Bind: " + string), x + 2, y + 3 + height * m, false, Color.WHITE);
                if (mouseX >= x && mouseY >= y + m * height && mouseX <= x + width && mouseY <= y + height + m * height) {
                    if (clickedButton == 0) module.setBinding(!module.isBinding());
                    if (module.isBinding() && key != -1) {
                        if (key == GLFW.GLFW_KEY_BACKSPACE)
                            module.setBind(-1, 0);
                        else {
                            module.setBind(key, modifiers);
                        }
                        module.setBinding(false);
                    }
                }
                m++;
            }
            RenderUtils.drawRectOutLine(matrix, x, y + o * height, x + width, y + m * height, new Color(100, 141, 184));

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
        modifiers = -1;
    }

    void mouseClicked(double mouseX, double mouseY, int button) {
        clickedButton = button;
        if (button == 0) dragging = true;
    }

    void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        this.dragging = false;
    }

    void keyPressed(int key, int modifiers) {
        this.key = key;
        this.modifiers = modifiers;
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
