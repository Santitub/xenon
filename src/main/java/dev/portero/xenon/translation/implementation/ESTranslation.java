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
public class ESTranslation extends AbstractTranslation {

    ESTranslation(Language language) {
        super(language);
    }

    ESTranslation() {
        this(Language.ES);
    }

    @Description({
        " ",
        "# Esta sección es responsable de todos los mensajes utilizados durante el mal uso de un argumento de comando.",
        "# Se encarga de los mensajes relacionados con el argumento en sí, como errores, uso y más.",
        " "
    })
    public ENArgumentSection argument = new ENArgumentSection();

    @Getter
    @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        @Description("# {PERMISSIONS} - El permiso específico requerido para ejecutar el comando correspondiente.")
        public Notice permissionMessage = Notice.chat("<red>No tienes permiso para ejecutar este comando! <red>(<gray>{PERMISSIONS}<red>)");

        @Description({" ", "# {USAGE} - La manera correcta de utilizar el comando correspondiente."})
        public Notice usageMessage = Notice.chat("<red>Uso: <gray>{USAGE}");
        public Notice usageMessageHead = Notice.chat("<red>Uso correcto:");
        public Notice usageMessageEntry = Notice.chat("<red>- <gray>{USAGE}");

        @Description(" ")
        public Notice offlinePlayer = Notice.chat("<red>El jugador al que intentas acceder está actualmente desconectado.");
        public Notice onlyPlayer = Notice.chat("<red>Este comando está reservado solo para jugadores.");
        public Notice numberBiggerThanOrEqualZero = Notice.chat("<red>Introduce un número que sea cero o positivo.");
        public Notice noItem = Notice.chat("<red>Se requiere un objeto para ejecutar este comando.");
        public Notice noMaterial = Notice.chat("<red>El objeto especificado no se encontró.");
        public Notice noArgument = Notice.chat("<red>Argumento no reconocido. Por favor, verifica e intenta de nuevo.");
        public Notice noDamaged = Notice.chat("<red>El objeto no está dañado y no se puede reparar.");
        public Notice noDamagedItems = Notice.chat("<red>Se necesitan objetos dañados para usar este comando.");
        public Notice noEnchantment = Notice.chat("<red>El encantamiento especificado no existe.");
        public Notice noValidEnchantmentLevel = Notice.chat("<red>Nivel de encantamiento no compatible.");
        public Notice invalidTimeFormat = Notice.chat("<red>El formato de tiempo es incorrecto. Por favor, corrígelo.");
        public Notice worldDoesntExist = Notice.chat("<red>No existe tal mundo. Por favor, verifica el nombre del mundo.");
        public Notice youMustGiveWorldName = Notice.chat("<red>Se debe especificar un nombre de mundo para este comando.");
        public Notice incorrectLocation = Notice.chat("<red>La ubicación proporcionada es incorrecta. Por favor, verifica.");
        public Notice incorrectNumberOfChunks = Notice.chat("<red>La cantidad de fragmentos especificada es incorrecta.");
    }

    @Description({
        " ",
        "# Esta sección es responsable del formato general de algunos valores.",
        "# El propósito de la sección es reducir la repetición de algunos mensajes.",
        " "
    })
    public ENFormatSection format = new ENFormatSection();

    @Getter
    @Contextual
    public static class ENFormatSection implements Format {
        public String enable = "<green>activado";
        public String disable = "<red>desactivado";
    }

    @Description({
        " ",
        "# Esta sección es responsable de asuntos relacionados con los jugadores e interacciones con ellos.",
        "# Se encarga de los mensajes relacionados con el jugador, como comandos, eventos y más.",
        " "
    })
    public ENPlayerSection player = new ENPlayerSection();

    @Getter
    @Contextual
    public static class ENPlayerSection implements PlayerSection {
        @Description("# {STATE} - El estado del modo volar de ese jugador en particular.")
        public Notice flyEnable = Notice.chat("<gray>El vuelo ahora está {STATE}");
        public Notice flyDisable = Notice.chat("<gray>El vuelo ahora está {STATE}");

        @Description({" ", "# {PLAYER} - El jugador especificado en el comando, {STATE} - El estado del modo volar de ese jugador en particular."})
        public Notice flySetEnable = Notice.chat("<gray>El vuelo para <yellow>{PLAYER} <gray>ahora está {STATE}");
        public Notice flySetDisable = Notice.chat("<gray>El vuelo para <yellow>{PLAYER} <gray>ahora está {STATE}");

        @Description({" ", "# {GAMEMODE} - El modo de juego especificado en el comando, {PLAYER} - El jugador especificado en el comando."})
        public Notice gameModeNotCorrect = Notice.chat("<red>El modo de juego especificado no es correcto.");
        public Notice gameModeMessage = Notice.chat("<gray>Tu modo de juego ha sido establecido a <yellow>{GAMEMODE}");
        public Notice gameModeSetMessage = Notice.chat("<gray>El modo de juego de <yellow>{PLAYER} <gray>ha sido establecido a <yellow>{GAMEMODE}");
    }
}
