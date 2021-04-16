package org.quantumclient.qubit.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import org.quantumclient.qubit.utils.Wrapper;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PlayerArgument implements ArgumentType<PlayerEntity>, Wrapper {

    private final DynamicCommandExceptionType invalidArg = new DynamicCommandExceptionType(o ->
            new LiteralText(o + " can not be added."));

    @Override
    public PlayerEntity parse(StringReader reader) throws CommandSyntaxException {
        String argument = reader.readString();
        PlayerEntity playerEntity = null;
        assert mc.world != null;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p.getDisplayName().asString().equalsIgnoreCase(argument)) {
                playerEntity = p;
                break;
            }
        }
        if (playerEntity == null) throw invalidArg.create(argument);
        return playerEntity;
    }

    @Override
    public Collection<String> getExamples() {
        return (mc.world != null
                ? (mc.world.getPlayers()
                .stream()
                .map(playerEntity -> playerEntity.getDisplayName().asString())
                .collect(Collectors.toList())) : Collections.singleton("ChiquitaV2"));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        assert mc.world != null;
        return CommandSource.suggestMatching(mc.world.getPlayers().stream().map(playerEntity -> playerEntity.getDisplayName().getString()), builder);
    }

}
