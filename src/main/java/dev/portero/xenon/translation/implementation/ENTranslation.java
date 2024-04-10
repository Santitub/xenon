package dev.portero.xenon.translation.implementation;

import dev.portero.xenon.feature.language.Language;
import dev.portero.xenon.multification.notice.Notice;
import dev.portero.xenon.translation.AbstractTranslation;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
public class ENTranslation extends AbstractTranslation {

    ENTranslation(Language language) {
        super(language);
    }

    ENTranslation() {
        this(Language.EN);
    }

    @Description({
            "#",
            "# This file is responsible for the English translation in Xenon.",
            "#",
    })

    @Description({
            " ",
            "# You can use MiniMessages formatting everywhere, or standard &7, &e etc.",
            "# More information about MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
            "# You can use the web generator to generate and preview messages: https://webui.adventure.kyori.net/",
            "#",
            "# The new notification system supports various formats and options:",
            "#",
            "# Examples:",
            "#",
            "# example: []",
            "#",
            "# example: \"Hello world\"",
            "#",
            "# example:",
            "#   - \"Hello\"",
            "#   - \"world\"",
            "#",
            "# example:",
            "#   title: \"Hello world\"",
            "#   subtitle: \"Subtitle\"",
            "#   times: \"1s 2s 1s\"",
            "#",
            "# example:",
            "#   subtitle: \"Subtitle\"",
            "#   chat: \"Death message\"",
            "#",
            "# example:",
            "#   actionbar: \"Hello world\"",
            "#   chat:",
            "#     - \"Hello\"",
            "#     - \"world\"",
            "#",
            "# example:",
            "#   actionbar: \"Hello world\"",
            "#   # https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html ",
            "#   sound: \"ENTITY_PLAYER_LEVELUP 2.0 1.0\"",
            "#",
            "# example:",
            "#   actionbar: \"Hello world\"",
            "#   # Sound categories: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/SoundCategory.html",
            "#   sound: \"ENTITY_PLAYER_LEVELUP WEATHER 2.0 1.0\" # If you want to play a sound in a certain category, for example if a player has the sound category \"WEATHER\" in the game settings set to 0%, the sound will not play.",
            "#",
            "# example:",
            "#   titleHide: true # This clearing the title from the screen, example if other plugin send title you can clear it with this for any action",
            "#",
            " "
    })
    @Description("# This section is responsible for all messages used during bad of a command argument")
    public ENArgumentSection argument = new ENArgumentSection();

    @Getter
    @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        @Description("# {PERMISSIONS} - Required permission")
        public Notice permissionMessage = Notice.chat("<red>✘ <dark_red>You don't have permission to perform this command! <red>({PERMISSIONS})");

        @Description({" ", "# {USAGE} - Correct usage"})
        public Notice usageMessage = Notice.chat("<gold>✘ <white>Correct usage: <gold>{USAGE}");
        public Notice usageMessageHead = Notice.chat("<green>► <white>Correct usage:");
        public Notice usageMessageEntry = Notice.chat("<green>► <white>{USAGE}");

        @Description(" ")
        public Notice offlinePlayer = Notice.chat("<red>✘ <dark_red>This player is currently offline!");
        public Notice onlyPlayer = Notice.chat("<red>✘ <dark_red>Command is only for players!");
        public Notice numberBiggerThanOrEqualZero = Notice.chat("<red>✘ <dark_red>The number must be greater than or equal to 0!");
        public Notice noItem = Notice.chat("<red>✘ <dark_red>You need item to use this command!");
        public Notice noMaterial = Notice.chat("<red>✘ <dark_red>This item doesn't exist");
        public Notice noArgument = Notice.chat("<red>✘ <dark_red>This argument doesn't exist");
        public Notice noDamaged = Notice.chat("<red>✘ <dark_red>This item can't be repaired");
        public Notice noDamagedItems = Notice.chat("<red>✘ <dark_red>You need damaged items to use this command!");
        public Notice noEnchantment = Notice.chat("<red>✘ <dark_red>This enchantment doesn't exist");
        public Notice noValidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>This enchantment level is not supported!");
        public Notice invalidTimeFormat = Notice.chat("<red>✘ <dark_red>Invalid time format!");
        public Notice worldDoesntExist = Notice.chat("<red>✘ <dark_red>This world doesn't exist!");
        public Notice youMustGiveWorldName = Notice.chat("<red>✘ <dark_red>You must provide a world name!");
        public Notice incorrectLocation = Notice.chat("<red>✘ <dark_red>Incorrect location!");
        public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Incorrect number of chunks!");
    }

    @Description({
            " ",
            "# This answer is responsible for the general formatting of some values",
            "# The purpose of the section is to reduce the repetition of some messages."
    })
    public ENFormatSection format = new ENFormatSection();

    @Getter
    @Contextual
    public static class ENFormatSection implements Format {
        public String enable = "<green>enabled";
        public String disable = "<red>disabled";
    }

    @Description({
            " ",
            "# This section is responsible for player-related stuff and interactions with them."
    })
    public ENPlayerSection player = new ENPlayerSection();

    @Getter
    @Contextual
    public static class ENPlayerSection implements PlayerSection {
        @Description({" ", "# {STATE} - Fly status"})
        public Notice flyEnable = Notice.chat("<green>► <white>Fly is now {STATE}");
        public Notice flyDisable = Notice.chat("<green>► <white>Fly is now {STATE}");

        @Description("# {PLAYER} - Target player, {STATE} - Target player fly status")
        public Notice flySetEnable = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
        public Notice flySetDisable = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
    }

}
