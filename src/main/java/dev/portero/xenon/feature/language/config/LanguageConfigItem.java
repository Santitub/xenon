package dev.portero.xenon.feature.language.config;

import dev.portero.xenon.configuration.contextual.ConfigItem;
import dev.portero.xenon.feature.language.Language;
import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.List;

@Contextual
public class LanguageConfigItem extends ConfigItem {

    public Language language = Language.EN;

    public LanguageConfigItem(String name, List<String> lore, Material material, String texture, boolean glow, int slot, List<String> commands, Language language) {
        super(name, lore, material, texture, glow, slot, commands);
        this.language = language;
    }

    public LanguageConfigItem() {
    }
}