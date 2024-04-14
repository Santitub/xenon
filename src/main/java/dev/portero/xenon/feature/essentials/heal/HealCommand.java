package dev.portero.xenon.feature.essentials.heal;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "heal")
public class HealCommand {

    @Execute
    public void execute(@Context Player sender) {
        sender.setHealth(20);
        sender.setFoodLevel(20);
        sender.setSaturation(20);
        sender.sendMessage("You have been healed!");
    }
}
