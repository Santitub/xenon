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
        " ",
        "# This section is responsible for all messages used during bad of a command argument.",
        "# It is responsible for messages related to the argument itself, such as errors, usage, and more.",
        " "
    })
    public ENArgumentSection argument = new ENArgumentSection();

    @Getter
    @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        @Description("# {PERMISSIONS} - The specific permission required to execute the associated command.")
        public Notice permissionMessage = Notice.chat("<red>You don't have permission to perform this command! <red>(<gray>{PERMISSIONS}<red>)");

        @Description({" ", "# {USAGE} - The correct way to use the associated command."})
        public Notice usageMessage = Notice.chat("<red>Usage: <gray>{USAGE}");
        public Notice usageMessageHead = Notice.chat("<red>Correct usage:");
        public Notice usageMessageEntry = Notice.chat("<red>- <gray>{USAGE}");

        @Description(" ")
        public Notice offlinePlayer = Notice.chat("<red>The player you're trying to reach is currently not online.");
        public Notice onlyPlayer = Notice.chat("<red>This command is reserved for player use only.");
        public Notice numberBiggerThanOrEqualZero = Notice.chat("<red>Enter a number that is zero or positive.");
        public Notice noItem = Notice.chat("<red>An item is required to execute this command.");
        public Notice noMaterial = Notice.chat("<red>The specified item was not found.");
        public Notice noArgument = Notice.chat("<red>Unrecognized argument. Please check and try again.");
        public Notice noDamaged = Notice.chat("<red>The item is not damaged and cannot be repaired.");
        public Notice noDamagedItems = Notice.chat("<red>Damaged items are needed to use this command.");
        public Notice noEnchantment = Notice.chat("<red>The enchantment specified does not exist.");
        public Notice noValidEnchantmentLevel = Notice.chat("<red>Unsupported enchantment level.");
        public Notice invalidTimeFormat = Notice.chat("<red>The time format is incorrect. Please correct it.");
        public Notice worldDoesntExist = Notice.chat("<red>No such world exists. Please verify the world name.");
        public Notice youMustGiveWorldName = Notice.chat("<red>A world name must be specified for this command.");
        public Notice incorrectLocation = Notice.chat("<red>The location provided is incorrect. Please verify.");
        public Notice incorrectNumberOfChunks = Notice.chat("<red>The specified chunk count is incorrect.");
    }

    @Description({
        " ",
        "# This answer is responsible for the general formatting of some values.",
        "# The purpose of the section is to reduce the repetition of some messages.",
        " "
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
        "# This section is responsible for player-related stuff and interactions with them.",
        "# It is responsible for messages related to the player, such as commands, events, and more.",
        " "
    })
    public ENPlayerSection player = new ENPlayerSection();

    @Getter
    @Contextual
    public static class ENPlayerSection implements PlayerSection {
        @Description("# {STATE} - The current fly status of a player.")
        public Notice flyEnable = Notice.chat("<gray>Fly is now {STATE}");
        public Notice flyDisable = Notice.chat("<gray>Fly is now {STATE}");

        @Description({" ", "# {PLAYER} - The specified player in the command, {STATE} - The current fly status of a player."})
        public Notice flySetEnable = Notice.chat("<gray>Fly for <yellow>{PLAYER} <gray>is now {STATE}");
        public Notice flySetDisable = Notice.chat("<gray>Fly for <yellow>{PLAYER} <gray>is now {STATE}");
    }

}
