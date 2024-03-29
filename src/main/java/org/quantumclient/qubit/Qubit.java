package org.quantumclient.qubit;

import net.fabricmc.api.ModInitializer;
import org.quantumclient.energy.EventBus;
import org.quantumclient.qubit.managers.CommandManager;
import org.quantumclient.qubit.managers.ConfigManager;
import org.quantumclient.qubit.managers.ManagerManager;
import org.quantumclient.qubit.managers.ModuleManager;
import org.quantumclient.qubit.utils.Wrapper;

public class Qubit implements ModInitializer, Wrapper {

    public static final String NAME = "Qubit";

    protected static final String VERSION = "1.0.1";

    protected static final EventBus eventBus = new EventBus();

    protected static ManagerManager managerManager = new ManagerManager();
    protected static ModuleManager moduleManger = new ModuleManager();
    protected static ConfigManager configManager = new ConfigManager();
    protected static CommandManager commandManager = new CommandManager();

    @Override
    public void onInitialize() {
        managerManager.init();
        mc.execute(() -> mc.getWindow().setTitle(String.format("%s - %s", NAME, VERSION)));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> configManager.save()));
    }

    public static ConfigManager getConfigManger() {
        return configManager;
    }

    public static ModuleManager getModuleManger() {
        return moduleManger;
    }

    public static CommandManager getCommandManger() {
        return commandManager;
    }

    public static ManagerManager getMangerManger() {
        return managerManager;
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

}
