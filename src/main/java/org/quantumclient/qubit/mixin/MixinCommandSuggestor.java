package org.quantumclient.qubit.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.screen.CommandSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.mangers.CommandManger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;

@Mixin(CommandSuggestor.class)
public abstract class MixinCommandSuggestor {

    @Shadow
    private ParseResults<CommandSource> parse;

    @Shadow
    @Final
    private TextFieldWidget textField;

    @Shadow
    private boolean completingSuggestions;

    @Shadow
    private CompletableFuture<Suggestions> pendingSuggestions;

    @Shadow
    protected abstract void show();

    @Inject(method = "refresh", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onRefresh(CallbackInfo ci, String string, StringReader reader) {
        int length = CommandManger.PREFIX.length();
        if (reader.canRead(length) && reader.getString().startsWith(CommandManger.PREFIX, reader.getCursor())) {
            reader.setCursor(reader.getCursor() + length);

            CommandDispatcher<CommandSource> commandDispatcher = Qubit.getCommandManger().getDispatcher();
            if (this.parse == null) {
                this.parse = commandDispatcher.parse(reader, Qubit.getCommandManger().getClientCommandSource());
            }

            int i = textField.getCursor();
            if (i >= 1 && (!completingSuggestions)) {
                pendingSuggestions = commandDispatcher.getCompletionSuggestions(parse, i);
                pendingSuggestions.thenRun(() -> {
                    if (pendingSuggestions.isDone()) {
                        show();
                    }
                });
            }
        }
    }

}
