package org.quantumclient.qubit.managers;

import org.quantumclient.qubit.Qubit;

import java.util.ArrayList;
import java.util.List;

public class ManagerManager {

    private List<Manager> managerList = new ArrayList<>();

    public void init() {
        for (Manager manager : managerList) {
            Qubit.LOGGER.info(manager.getName() + " are being initialized");
            manager.init();
        }
    }

    public void add(Manager manager) {
        managerList.add(manager);
    }

    public interface Manager {
        String getName();

        void init();
    }

}
