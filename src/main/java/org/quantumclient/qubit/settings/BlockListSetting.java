package org.quantumclient.qubit.settings;

import net.minecraft.block.Block;

import java.util.List;

public class BlockListSetting extends Setting<List<Block>> {

    public BlockListSetting(String name, String description, List<Block> value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }
}
