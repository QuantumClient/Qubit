package org.quantumclient.qubit.managers;

import org.lwjgl.glfw.GLFW;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventKeyPress;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.module.client.ClickGui;
import org.quantumclient.qubit.module.client.Font;
import org.quantumclient.qubit.module.client.TestModule;
import org.quantumclient.qubit.module.client.ToggleMsg;
import org.quantumclient.qubit.module.combat.Aura;
import org.quantumclient.qubit.module.combat.Offhand;
import org.quantumclient.qubit.module.movement.ElytraFlight;
import org.quantumclient.qubit.module.movement.Sprint;
import org.quantumclient.qubit.module.player.NoFall;
import org.quantumclient.qubit.module.player.Scaffold;
import org.quantumclient.qubit.module.player.Velocity;
import org.quantumclient.qubit.module.render.*;
import org.quantumclient.qubit.module.world.Timer;
import org.quantumclient.qubit.module.world.Xray;
import org.quantumclient.qubit.utils.Bind;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.quantumclient.qubit.utils.Wrapper.mc;

public final class ModuleManager implements Manager {

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        Qubit.getMangerManger().add(this);
    }

    @Override
    public void init() {
        Qubit.getEventBus().register(this);
        add(new TestModule());
        add(new Sprint());
        add(new ClickGui());
        add(new Offhand());
        add(new ToggleMsg());
        add(new Aura());
        add(new Font());
        add(new Fullbright());
        add(new Velocity());
        add(new NoRender());
        add(new Timer());
        add(new Scaffold());
        add(new NoFall());
        add(new Australia());
        add(new ESP());
        add(new ElytraFlight());
        add(new Zoom());
        add(new Xray());
    }

    private void add(Module module) {
        module.loadSetting();
        modules.add(module);
    }

    public <M extends Module> M getModule(Class<M> module) {
        return (M) modules.stream()
                .filter(mod -> mod.getClass().equals(module))
                .findFirst()
                .orElse(null);
    }

    public boolean isModuleEnabled(Class<? extends Module> module) {
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
        if (mc.currentScreen != null) return;
        for (Module module : modules) {
            if (event.getAction() == GLFW.GLFW_PRESS) {
                if (module.getBind().pressMatches(event.getKey(), event.getModifiers())) {
                    module.toggle();
                    event.cancel();
                }
            } else if (event.getAction() == GLFW.GLFW_RELEASE && module.getBind().getType().equals(Bind.Type.HOLD) && module.getBind().pressMatches(event.getKey(), event.getModifiers())) {
                module.toggle();
                event.cancel();
            }

        }
    }

    @Override
    public String getName() {
        return "Modules";
    }

}
