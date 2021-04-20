package org.quantumclient.qubit;

import net.fabricmc.api.ModInitializer;
import org.quantumclient.qubit.mangers.*;
import org.quantumclient.qubit.utils.Wrapper;

import java.awt.*;

public class Qubit implements ModInitializer, Wrapper {

    public static final String NAME = "Qubit";
    protected static final String VERSION = "1.0";

    protected static ModuleManger moduleManger = new ModuleManger();
    protected static ConfigManger configManger = new ConfigManger();
    protected static CommandManger commandManger = new CommandManger();
    protected static FriendManger friendManger = new FriendManger();


    @Override
    public void onInitialize() {
        moduleManger.init();
        commandManger.init();
        configManger.init();
      //  fontManger.init();
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

}
