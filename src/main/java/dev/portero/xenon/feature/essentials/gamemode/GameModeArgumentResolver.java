package dev.portero.xenon.feature.essentials.gamemode;

import org.bukkit.GameMode;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameModeArgumentResolver {

    public Map<GameMode, List<String>> gameModeShortCuts = Map.of(
            GameMode.SURVIVAL, Collections.singletonList("gms"),
            GameMode.CREATIVE, Collections.singletonList("gmc"),
            GameMode.ADVENTURE, Collections.singletonList("gma"),
            GameMode.SPECTATOR, Collections.singletonList("gmsp")
    );
    public Map<GameMode, List<String>> gameModeAliases = Map.of(
            GameMode.SURVIVAL, List.of("survival", "0"),
            GameMode.CREATIVE, List.of("creative", "1"),
            GameMode.ADVENTURE, List.of("adventure", "2"),
            GameMode.SPECTATOR, List.of("spectator", "3")
    );

    public List<String> getGameModeShortCuts() {
        return this.gameModeShortCuts.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    public Optional<GameMode> getByAlias(String alias) {
        return this.gameModeAliases.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(alias))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Collection<String> getAvailableAliases() {
        return this.gameModeAliases.values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
    }
}
