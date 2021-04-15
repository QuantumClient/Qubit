package org.quantumclient.qubit.utils;

import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.quantumclient.qubit.Qubit;

public class MsgHelper implements Wrapper {

    private static String prefix = Formatting.GRAY + "[" + Formatting.DARK_AQUA + Qubit.NAME + Formatting.GRAY + "] " + Formatting.RESET;

    public static void sendMessage(String s) {
        if (mc.world != null) {
            mc.inGameHud.getChatHud().addMessage(new LiteralText(prefix + s));
        }
    }

}
