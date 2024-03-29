package org.quantumclient.qubit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quantumclient.qubit.utils.Wrapper;

public abstract class Command implements Wrapper {

    @NotNull
    protected String name;

    @Nullable
    protected String description;

    /**
     *
     * @param name what the command is called and how people would invoke it
     * @param description what the command does
     */
    protected Command(@NotNull String name, @Nullable String description) {
        this.name = name;
        this.description = description;
    }

    protected Command(String name) {
        this(name, null);
    }

    public abstract void register(CommandDispatcher<CommandSource> dispatcher);

    protected <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    protected LiteralArgumentBuilder<CommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

}
