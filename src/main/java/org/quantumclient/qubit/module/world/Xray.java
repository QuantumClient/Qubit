package org.quantumclient.qubit.module.world;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.BlockListSetting;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.Setting;
import org.quantumclient.qubit.utils.annotations.AddSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

import java.util.ArrayList;
import java.util.List;

@Info("Xray")
@SetCategory(Category.WORLD)
public class Xray extends Module {

    private List<Block> xrayList = new ArrayList<>();

    //@AddSetting
    //public BlockListSetting xrayListSetting = new BlockListSetting("List", null, new ArrayList<>());

    public Xray() {
        super();
        xrayList.add(Blocks.DIAMOND_ORE);
        xrayList.add(Blocks.WHITE_WOOL);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        if (mc.world != null) mc.worldRenderer.reload();

    }

    @Override
    protected void onDisable() {
        super.onDisable();
        if (mc.world != null) mc.worldRenderer.reload();

    }

    public boolean isinList(Block block) {
        return  xrayList.contains(block);
    }

}
