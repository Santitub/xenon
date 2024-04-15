package dev.portero.xenon.translation;

import dev.portero.xenon.feature.language.Language;
import dev.portero.xenon.multification.notice.Notice;

public interface Translation {

    // Language section
    Language getLanguage();

    // Argument section
    ArgumentSection argument();

    interface ArgumentSection {

        Notice permissionMessage();

        Notice usageMessage();

        Notice usageMessageHead();

        Notice usageMessageEntry();

        Notice offlinePlayer();

        Notice onlyPlayer();

        Notice numberBiggerThanOrEqualZero();

        Notice noItem();

        Notice noArgument();

        Notice noDamaged();

        Notice noDamagedItems();

        Notice noEnchantment();

        Notice noValidEnchantmentLevel();

        Notice invalidTimeFormat();

        Notice worldDoesntExist();

        Notice youMustGiveWorldName();

        Notice incorrectNumberOfChunks();

        Notice incorrectLocation();

    }

    // format section
    Format format();

    // Format section
    interface Format {

        String enable();

        String disable();
    }

    // Player section
    PlayerSection player();

    interface PlayerSection {

        // Fly section
        Notice flyEnable();

        Notice flyDisable();

        Notice flySetEnable();

        Notice flySetDisable();

        // GameMode section
        Notice gameModeNotCorrect();

        Notice gameModeMessage();

        Notice gameModeSetMessage();

        // Heal section
        Notice healPlayer();

        // Join section
        Notice joinMessage();
    }
}
