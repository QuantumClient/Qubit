package org.quantumclient.qubit.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.Nullable;

public abstract class Command {

    protected String name;

    @Nullable
    protected String description;


    public Command(String name, @Nullable String description) {
        this.name = name;
        this.description = description;
    }

    public Command(String name) {
        this(name, null);
    }

    public abstract void register(CommandDispatcher<CommandSource> dispatcher);

}
