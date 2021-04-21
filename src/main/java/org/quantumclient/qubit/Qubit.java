package org.quantumclient.qubit;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quantumclient.qubit.mangers.*;
import org.quantumclient.qubit.utils.Wrapper;

public class Qubit implements ModInitializer, Wrapper {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String NAME = "Qubit";

    protected static final String VERSION = "1.0.0";

    protected static MangerManger mangerManger = new MangerManger();
    protected static FriendManger friendManger = new FriendManger();
    protected static ModuleManger moduleManger = new ModuleManger();
    protected static ConfigManger configManger = new ConfigManger();
    protected static CommandManger commandManger = new CommandManger();

    @Override
    public void onInitialize() {
        mangerManger.init();
        mc.execute(() -> mc.getWindow().setTitle(String.format("%s - %s", NAME, VERSION)));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> configManger.save()));
    }

    public static ConfigManger getConfigManger() {
        return configManger;
    }

    public static ModuleManger getModuleManger() {
        return moduleManger;
    }

    public static CommandManger getCommandManger() {
        return commandManger;
    }

    public static FriendManger getFriendManger() {
        return friendManger;
    }

    public static MangerManger getMangerManger() {
        return mangerManger;
    }

}
