package org.quantumclient.qubit.mangers;

import org.quantumclient.qubit.Qubit;

import java.util.ArrayList;
import java.util.List;

public class MangerManger {

    private List<Manger> mangerList = new ArrayList<>();

    public void init() {
        //i'm pretty sure this who thing slows down the client but idc
        for (Manger manger : mangerList) {
            Qubit.LOGGER.info(manger.getName() + " are being initialized");
            manger.init();
        }
    }

    public void add(Manger manger) {
        mangerList.add(manger);
    }

    public interface Manger {
        String getName();

        void init();
    }

}
