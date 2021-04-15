package org.quantumclient.qubit;

import net.fabricmc.api.ModInitializer;
import org.quantumclient.qubit.mangers.ConfigManger;
import org.quantumclient.qubit.mangers.ModuleManger;
import org.quantumclient.qubit.utils.Wrapper;

public class Qubit implements ModInitializer, Wrapper {

    public static final String NAME = "Qubit";
    protected static final String VERSION = "1.0";

    protected static ModuleManger moduleManger = new ModuleManger();
    protected static ConfigManger configManger = new ConfigManger();

    @Override
    public void onInitialize() {
        moduleManger.init();
        configManger.init();
        mc.execute(() -> mc.getWindow().setTitle(String.format("%s - %s", NAME, VERSION)));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> configManger.save()));
    }

    public static ConfigManger getConfigManger() {
        return configManger;
    }

    public static ModuleManger getModuleManger() {
        return moduleManger;
    }
}
