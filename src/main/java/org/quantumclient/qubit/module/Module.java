package org.quantumclient.qubit.module;

import net.minecraft.util.Formatting;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.client.ToggleMsg;
import org.quantumclient.qubit.settings.AddSetting;
import org.quantumclient.qubit.settings.Setting;
import org.quantumclient.qubit.utils.MsgUtils;
import org.quantumclient.qubit.utils.Wrapper;
import org.quantumclient.qubit.utils.annotations.Silent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Module implements Wrapper {

    @NotNull
    private final String name;

    @Nullable
    private final String description;

    @NotNull
    private final Category category;

    protected boolean open = false;

    private boolean isBinding = false;
    private int bind;
    private boolean toggled;
    private List<Setting> settingList;

    /**
     * Constructor
     * @param name the name of the module
     * @param description what the module does
     * @param bind a key where the button would turn on
     * @param category what type of module it is
     */
    public Module(@NotNull String name, @Nullable String description, int bind, @NotNull Category category) {
        this.name = name;
        this.description = description;
        this.bind = bind;
        this.category = category;
    }

    public Module(String name, String description, Category category) {
        this(name, description, -1, category);
    }

    public Module(String name, Category category, int bind) {
        this(name, null, bind, category);
    }

    public Module(String name, Category category) {
        this(name, null, category);
    }

    /**
     * The name of the client
     * @return what the module is called
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * What category type the module is
     * @return what category the module is in
     */
    public @NotNull Category getCategory() {
        return category;
    }

    protected void onEnable() {
        if (getClass().getAnnotation(Silent.class) == null && Qubit.getModuleManger().isModuleEnabled(ToggleMsg.class)) {
            MsgUtils.sendMessage(name + Formatting.GREEN + " enabled");
        }
        toggled = true;
        Qubit.getEventBus().register(this);
    }

    protected void onDisable() {
        if (getClass().getAnnotation(Silent.class) == null) {
            MsgUtils.sendMessage(name + Formatting.RED + " disable");
        }
        toggled = false;
        Qubit.getEventBus().unregister(this);
    }

    public void toggle() {
        if (toggled) {
            onDisable();
        } else {
            onEnable();
        }
    }

    /**
     * Weather the client should be on
     * @param toggled weather the module should be on or not
     */
    public void setToggled(boolean toggled) {
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Settings used for controlling variables in modules
     * @param setting adds a setting to the setting list
     */
    protected void addSetting(Setting setting) {
        if (settingList == null) {
            settingList = new ArrayList<>();
        }
        settingList.add(setting);
    }

    /**
     * Settings used for controlling variables in modules
     * @param settings allows you to add multiple settings at once
     */
    protected void addSetting(Setting... settings) {
        for (Setting setting : settings) {
            addSetting(setting);
        }
    }

    protected void setSettingList(List<Setting> settingList) {
        this.settingList = settingList;
    }

    public List<Setting> getSettingList() {
        return settingList;
    }

    public boolean isToggled() {
        return toggled;
    }

    public boolean hasSetting() {
        return settingList != null && !settingList.isEmpty();
    }

    public int getBind() {
        return bind;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isBinding() {
        return isBinding;
    }

    public void setBinding(boolean binding) {
        isBinding = binding;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public @Nullable String getDescription() {
        return description;
    }

    /**
     * Used for loading setting added though the AddSetting annotation
     */
    public void loadSetting() {
        try {
            for (Field field : FieldUtils.getFieldsWithAnnotation(getClass(), AddSetting.class)) {
                if (!field.isAccessible()) field.setAccessible(true);
                if (Setting.class.isAssignableFrom(field.getType())) {
                    addSetting((Setting) field.get(this));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
