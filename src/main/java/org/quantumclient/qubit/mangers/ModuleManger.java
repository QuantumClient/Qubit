package org.quantumclient.qubit.mangers;

import org.lwjgl.glfw.GLFW;
import org.quantumclient.energy.EventBus;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventKeyPress;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.module.client.ClickGui;
import org.quantumclient.qubit.module.client.Font;
import org.quantumclient.qubit.module.client.TestModule;
import org.quantumclient.qubit.module.client.ToggleMsg;
import org.quantumclient.qubit.module.combat.Aura;
import org.quantumclient.qubit.module.combat.Offhand;
import org.quantumclient.qubit.module.movement.Sprint;
import org.quantumclient.qubit.module.player.Velocity;
import org.quantumclient.qubit.module.render.Fullbight;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.quantumclient.qubit.utils.Wrapper.mc;

public class ModuleManger {

    private final List<Module> modules = new ArrayList<>();

    public void init() {
        EventBus.register(this);
        add(new TestModule());
        add(new Sprint());
        add(new ClickGui());
        add(new Offhand());
        add(new ToggleMsg());
        add(new Aura());
        add(new Font());
        add(new Fullbight());
        add(new Velocity());
    }

    private void add(Module module) {
        modules.add(module);
    }

    public Module getModule(Class<? extends Module> module) {
        return modules.stream()
                .filter(mod -> mod.getClass().equals(module))
                .findFirst()
                .orElse(null);
    }

    public Module getModule(String name) {
        return modules.stream()
                .filter(mod -> mod.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public boolean isModuleEnabled(Class<? extends Module> module) {
        return getModule(module).isToggled();
    }

    public boolean isModuleEnabled(String module) {
        return getModule(module).isToggled();
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesInCat(Category category) {
       return modules.stream()
               .filter(module -> module.getCategory().equals(category))
               .sorted(Comparator.comparing(Module::getName))
               .collect(Collectors.toList());
    }

    @Subscribe
    public void onKeyPress(EventKeyPress event) {
        if (event.getAction() != GLFW.GLFW_PRESS) return;
        if (mc.currentScreen != null) return;
        for (Module module : modules) {
            if (module.getBind() == event.getKey()) {
                module.toggle();
            }
        }
    }

}
