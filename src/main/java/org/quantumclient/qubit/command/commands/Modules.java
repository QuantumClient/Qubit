package org.quantumclient.qubit.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.command.Command;
import org.quantumclient.qubit.module.Module;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;

public class Modules extends Command {

    public Modules() {
        super("Modules");
    }

    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        for (Module module : Qubit.getModuleManger().getModules()) {
            LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(module.getName());
            builder.then(argument("Toggle", bool())
                    .executes(context -> {
                        module.setToggled(getBool(context, "Toggle"));
                        return SINGLE_SUCCESS;
                    }));
            dispatcher.register(builder);
        }
    }

}
