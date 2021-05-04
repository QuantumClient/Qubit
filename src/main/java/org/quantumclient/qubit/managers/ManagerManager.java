package org.quantumclient.qubit.managers;

import org.quantumclient.qubit.Qubit;

import java.util.ArrayList;
import java.util.List;

public final class ManagerManager {

    private final List<Manager> managerList = new ArrayList<>();

    public void init() {
        for (Manager manager : managerList) {
            Qubit.LOGGER.info(manager.getName() + " are being initialized");
            manager.init();
        }
    }

    public void add(Manager manager) {
        managerList.add(manager);
    }

}
