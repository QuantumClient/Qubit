package org.quantumclient.qubit.module.combat;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.event.EventTick;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Info(value = "Offhand", description = "Sets your offhand")
@SetCategory(Category.COMBAT)
public class Offhand extends Module {

    public ModeSetting modeSetting = new ModeSetting("Mode", "Totem", new String[] {"Totem", "Gapple", "Crystal"});
    public FloatSetting health = new FloatSetting("Health", "What hp to force a totem", 8f, 0f, 36f, 0f, 1);
    public CheckSetting elytraTotem = new CheckSetting("ElytraTotem", "Force a totem when flying", true);

    public Offhand() {
        super();
        addSetting(modeSetting);
        addSetting(health);
        addSetting(elytraTotem);
    }

    @Subscribe
    public void onTick(EventTick event) {
        if (mc.player.getOffHandStack().getItem() == getOffhandItem()) return;
        for (int i = 0; i < mc.player.inventory.size(); i++) {
            if (mc.player.getOffHandStack().getItem() != getOffhandItem()) {
                if (mc.player.inventory.getStack(i).getItem() == getOffhandItem()) {
                    mc.interactionManager.clickSlot(0, 45, 0, SlotActionType.PICKUP, mc.player);
                    mc.interactionManager.clickSlot(0, (i < 9) ? (i + 36) : i, 0, SlotActionType.PICKUP, mc.player);
                    mc.interactionManager.clickSlot(0, 45, 0, SlotActionType.PICKUP, mc.player);
                    break;
                }
            }
        }
    }

    private Item getOffhandItem() {
        String offhandValue = modeSetting.getValue();
        if (offhandValue.equals("Totem") || (mc.player.getHealth() + mc.player.getAbsorptionAmount()) < health.getValue() || (mc.player.isFallFlying() && elytraTotem.getValue()))
            return Items.TOTEM_OF_UNDYING;
        if (offhandValue.equals("Gapple"))
            return Items.ENCHANTED_GOLDEN_APPLE;
        if (offhandValue.equals("Crystal"))
            return Items.END_CRYSTAL;
        return Items.TOTEM_OF_UNDYING;
    }


}
