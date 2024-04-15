package dev.portero.xenon.feature.command.gamemode;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteCommandEditor;
import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.editor.Editor;

import java.util.ArrayList;

@LiteCommandEditor(command = GameModeCommand.class)
class GameModeConfigurator<SENDER> implements Editor<SENDER> {

    private final GameModeCommand gameModeCommand;

    @Inject
    GameModeConfigurator(GameModeCommand gameModeCommand) {
        this.gameModeCommand = gameModeCommand;
    }

    @Override
    public CommandBuilder<SENDER> edit(CommandBuilder<SENDER> context) {
        ArrayList<String> aliases = new ArrayList<>(context.aliases());
        aliases.addAll(this.gameModeCommand.getGameModeShortCuts());

        return context.aliases(aliases);
    }
}
