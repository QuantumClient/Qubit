package org.quantumclient.qubit.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import org.quantumclient.qubit.command.Command;
import org.quantumclient.qubit.utils.MsgHelper;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Test extends Command {

    public Test() {
        super("test", "i am testing");
    }

    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(name);
        builder.executes(context -> {
            MsgHelper.sendMessage("testing");
            return SINGLE_SUCCESS;
        });
        dispatcher.register(builder);
    }

}