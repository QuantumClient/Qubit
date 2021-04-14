package org.quantumclient.qubit.module;

import org.jetbrains.annotations.Nullable;
import org.quantumclient.energy.EventBus;
import org.quantumclient.qubit.settings.Setting;
import org.quantumclient.qubit.utils.Wrapper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Module implements Wrapper {


    private final String name;

    @Nullable
    private final String description;

    private final Category category;

    protected boolean open = false;

    private boolean isBinding = false;
    private int bind;
    private boolean toggled;
    private List<Setting> settingList;

    public Module(String name, @Nullable String description, int bind, Category category) {
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

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void onEnable() {
        toggled = true;
        EventBus.register(this);
    }

    public void onDisable() {
        toggled = false;
        EventBus.unregister(this);
    }

    public void toggle() {
        if (toggled) {
            onDisable();
        } else {
            onEnable();
        }
    }

    public void setToggled(boolean toggled) {
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    protected void addSetting(Setting setting) {
        if (settingList == null) {
            settingList = new ArrayList<>();
        }
        settingList.add(setting);
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

    public String getDescription() {
        return description;
    }

}
