package org.quantumclient.qubit.module.client;

import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;

public class ToggleMsg extends Module {

    public ToggleMsg() {
        super("ToggleMsg", "Alerts you when a module is togged", Category.CLIENT);
    }

}
