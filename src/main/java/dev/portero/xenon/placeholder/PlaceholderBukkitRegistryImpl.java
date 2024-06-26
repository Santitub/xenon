package dev.portero.xenon.placeholder;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.component.Service;
import dev.portero.xenon.viewer.Viewer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class PlaceholderBukkitRegistryImpl implements PlaceholderRegistry {

    private final Server server;
    private final Set<PlaceholderReplacer> replacerPlayers = new HashSet<>();
    private final Map<String, PlaceholderRaw> rawPlaceholders = new HashMap<>();

    @Inject
    public PlaceholderBukkitRegistryImpl(Server server) {
        this.server = server;
    }

    @Override
    public void registerPlaceholder(PlaceholderReplacer stack) {
        this.replacerPlayers.add(stack);

        if (stack instanceof PlaceholderRaw raw) {
            this.rawPlaceholders.put(raw.getRawTarget(), raw);
        }
    }

    @Override
    public String format(String text, Viewer target) {
        if (!target.isConsole()) {
            Player playerTarget = this.server.getPlayer(target.getUniqueId());

            if (playerTarget != null) {
                for (PlaceholderReplacer replacer : this.replacerPlayers) {
                    text = replacer.apply(text, playerTarget);
                }
            }
        }

        return text;
    }

    @Override
    public Optional<PlaceholderRaw> getRawPlaceholder(String target) {
        return Optional.ofNullable(this.rawPlaceholders.get(target));
    }
}
