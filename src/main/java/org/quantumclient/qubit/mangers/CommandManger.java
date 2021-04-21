package org.quantumclient.qubit.mangers;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.lwjgl.glfw.GLFW;
import org.quantumclient.energy.EventBus;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.command.Command;
import org.quantumclient.qubit.command.commands.Friends;
import org.quantumclient.qubit.command.commands.Test;
import org.quantumclient.qubit.event.EventKeyPress;
import org.quantumclient.qubit.event.EventPacketSend;
import org.quantumclient.qubit.utils.Wrapper;

public class CommandManger implements Wrapper, MangerManger.Manger {

    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();
    private final ClientCommandSource clientCommandSource = new ClientCommandSource(null, mc);
    public static String PREFIX = ",";

    @Override
    public void init() {
        EventBus.register(this);
        add(new Test());
        add(new Friends());
    }

    @Override
    public String getName() {
        return "Commands";
    }

    private void add(Command command) {
        command.register(dispatcher);
    }

    @Subscribe
    public void onPacketSend(EventPacketSend event) {
        if (event.getPacket() instanceof ChatMessageC2SPacket) {
            ChatMessageC2SPacket pack = (ChatMessageC2SPacket) event.getPacket();
            if (pack.getChatMessage().startsWith(PREFIX)) {
                event.setCancelled(true);
                try {
                    dispatcher.execute(pack.getChatMessage().substring(PREFIX.length()), clientCommandSource);
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Subscribe
    public void onKeyPress(EventKeyPress event) {
        if (event.getAction() != GLFW.GLFW_PRESS) return;
        if (mc.currentScreen != null) return;
        if (PREFIX.equals(InputUtil.fromKeyCode(event.getKey(), -1).getLocalizedText().getString())) {
            mc.openScreen(new ChatScreen(""));
        }
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return dispatcher;
    }

    public ClientCommandSource getClientCommandSource() {
        return clientCommandSource;
    }
}
