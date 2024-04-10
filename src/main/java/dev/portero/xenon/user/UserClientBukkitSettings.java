package dev.portero.xenon.user;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.UUID;

class UserClientBukkitSettings implements UserClientSettings {

    private final Server server;
    private final UUID uuid;
    private WeakReference<Player> playerReference;

    UserClientBukkitSettings(Server server, UUID uuid) {
        this.server = server;
        this.uuid = uuid;
        this.playerReference = new WeakReference<>(server.getPlayer(uuid));
    }

    private Option<Player> getPlayer() {
        Player player = this.playerReference.get();

        if (player == null) {
            Player playerFromServer = this.server.getPlayer(this.uuid);

            if (playerFromServer == null) {
                return Option.none();
            }

            this.playerReference = new WeakReference<>(playerFromServer);
            return Option.of(playerFromServer);
        }

        return Option.of(player);
    }

    private Player getPlayerOrThrow() {
        return this.getPlayer().orThrow(() -> new IllegalStateException("Player is offline"));
    }

    @Override
    public Locale getLocate() {
        return new Locale(this.getPlayerOrThrow().getLocale());
    }

    @Override
    public boolean isOnline() {
        return this.getPlayer().isPresent();
    }
}
