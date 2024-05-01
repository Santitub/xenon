package dev.portero.xenon.feature.command.time;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;

@Command(name = "time")
public class TimeCommand {

    @Execute
    @Permission("xenon.command.time")
    public void execute(@Context Player sender, @Arg String action, @Arg String time) {

        World world = sender.getWorld();

        if (action.equalsIgnoreCase("set")) {
            long ticks;
            try {
                ticks = Long.parseLong(time);
            } catch (NumberFormatException e) {
                ticks = this.parseTimeArgument(time);
            }

            world.setTime(ticks);
            sender.sendMessage(Component.text("Tiempo establecido a " + ticks + " ticks").color(NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Uso incorrecto del comando. Debes utilizar /time set <ticks>").color(NamedTextColor.RED));
        }
    }

    private long parseTimeArgument(String time) {

        switch (time.toLowerCase()) {
            case "day":
                return 1000;
            case "night":
                return 13000;
            case "midnight":
                return 18000;
            default:
                return 0;
        }
    }
}
