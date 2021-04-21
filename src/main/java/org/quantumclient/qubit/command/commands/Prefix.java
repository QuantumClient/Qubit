package org.quantumclient.qubit.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import org.quantumclient.qubit.command.Command;
import org.quantumclient.qubit.mangers.CommandManger;
import org.quantumclient.qubit.utils.MsgHelper;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;


public class Prefix extends Command {

    public Prefix() {
        super("Prefix", "Change what the prefix is");
    }

    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(name);
        builder.then(argument(name, string())
                .executes(context -> {
                    CommandManger.setPrefix(getString(context, name));
                    MsgHelper.sendMessage("prefix is now " + getString(context, name));
            return SINGLE_SUCCESS;
        }));
        dispatcher.register(builder);
    }
}
