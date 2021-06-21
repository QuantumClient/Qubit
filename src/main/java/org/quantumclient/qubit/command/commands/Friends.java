package org.quantumclient.qubit.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.command.Command;
import org.quantumclient.qubit.command.arguments.PlayerArgument;
import org.quantumclient.qubit.utils.MsgUtils;
import org.quantumclient.qubit.utils.Wrapper;
import org.utilitymods.friendapi.BaseProfile;
import org.utilitymods.friendapi.FriendManager;
import org.utilitymods.friendapi.Profile;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Friends extends Command {

    public Friends() {
        super("Friends", "Mange who your friends are");
    }

    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(name);
        builder.then(literal("add")
                .then(argument("name", new PlayerArgument())
                .executes(context -> {
                    PlayerEntity player = context.getArgument("name", PlayerEntity.class);
                    if (FriendManager.INSTANCE.isFriend(player.getUuid())) {
                        MsgUtils.sendMessage(player.getGameProfile().getName() + " is already a friend");
                    } else {
                        FriendManager.INSTANCE.addFriend(new Profile(player));
                        MsgUtils.sendMessage(player.getGameProfile().getName() + " is now a friend");
                    }
                    return SINGLE_SUCCESS;
                })))
                .then(literal("remove")
                .then(argument("name", new FriendArgument())
                .executes(context -> {
                    String player = context.getArgument("name", String.class);
                    UUID uuid = mc.getNetworkHandler().getPlayerListEntry(player).getProfile().getId();
                    FriendManager.INSTANCE.removeFriend(uuid);
                    MsgUtils.sendMessage(player + " is no longer a friend");
                    return SINGLE_SUCCESS;
                })))
                .then(literal("list")
                .executes(context -> {
                    for (BaseProfile profile : FriendManager.INSTANCE.getFriendMapCopy().values()) {
                        MsgUtils.sendMessage(profile.name);
                    }
                    return SINGLE_SUCCESS;
                }));
        dispatcher.register(builder);
    }

    private static class FriendArgument implements ArgumentType<String>, Wrapper {

        private final DynamicCommandExceptionType invalidArg = new DynamicCommandExceptionType(o ->
                new LiteralText(o + " is not a friend"));

        @Override
        public String parse(StringReader reader) throws CommandSyntaxException {
            String argument = reader.readString();
            String name = null;
            for (BaseProfile s : FriendManager.INSTANCE.getFriendMapCopy().values()) {
                if (s.name.equalsIgnoreCase(argument)) {
                    name = s.name;
                    break;
                }
            }
            if (name == null) throw invalidArg.create(argument);
            return name;
        }

        @Override
        public Collection<String> getExamples() {
            return FriendManager.INSTANCE.getFriendMapCopy().values().stream().map(profile -> profile.name).collect(Collectors.toList());
        }

        @Override
        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
            return CommandSource.suggestMatching(getExamples(), builder);
        }

    }
}
