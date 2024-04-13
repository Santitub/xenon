package dev.portero.xenon.translation;

import dev.portero.xenon.feature.language.Language;
import dev.portero.xenon.multification.notice.Notice;

public interface Translation {

    Language getLanguage();

    // argument section
    ArgumentSection argument();

    // format section
    Format format();

    // player section
    PlayerSection player();

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

    interface Format {
        String enable();

        String disable();
    }

    interface PlayerSection {
        Notice flyEnable();

        Notice flyDisable();

        Notice flySetEnable();

        Notice flySetDisable();
    }
}
