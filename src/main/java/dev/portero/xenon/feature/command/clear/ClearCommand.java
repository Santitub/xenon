package dev.portero.xenon.feature.command.clear;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;

@Command(name = "clear")
public class ClearCommand {

    @Execute
    @Permission("xenon.command.clear")
    public void execute(@Context Player sender) {
        sender.getInventory().clear();
        sender.sendMessage(Component.text("Inventario limpiado").color(NamedTextColor.GREEN));
    }

    @Execute
    @Permission("xenon.command.clear.others")
    public void execute(@Context Player sender, @Arg Player target) {
        target.getInventory().clear();
        sender.sendMessage(Component.text("Se ha limpiado el inventario de " + target.getName()).color(NamedTextColor.GREEN));
        target.sendMessage(Component.text("Tu inventario ha sido limpiado").color(NamedTextColor.RED));
    }
}
