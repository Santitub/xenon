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

    ESTranslation() {
        super(Language.ES);
    }

    @Description({
            "#",
            "# Este archivo es responsable de la traducción al español en Xenon.",
            "#",
    })

    @Description({
            " ",
            "# Puedes usar el formato MiniMessages en todas partes, o el estándar &7, &e, etc.",
            "# Más información sobre MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
            "# Puedes usar el generador web para generar y previsualizar mensajes: https://webui.adventure.kyori.net/",
            "#",
            "# El nuevo sistema de notificaciones soporta varios formatos y opciones:",
            "#",
            "# Ejemplos:",
            "#",
            "# ejemplo: []",
            "#",
            "# ejemplo: \"Hola mundo\"",
            "#",
            "# ejemplo:",
            "#   - \"Hola\"",
            "#   - \"mundo\"",
            "#",
            "# ejemplo:",
            "#   título: \"Hola mundo\"",
            "#   subtítulo: \"Subtítulo\"",
            "#   tiempos: \"1s 2s 1s\"",
            "#",
            "# ejemplo:",
            "#   subtítulo: \"Subtítulo\"",
            "#   chat: \"Mensaje de muerte\"",
            "#",
            "# ejemplo:",
            "#   barra de acción: \"Hola mundo\"",
            "#   chat:",
            "#     - \"Hola\"",
            "#     - \"mundo\"",
            "#",
            "# ejemplo:",
            "#   barra de acción: \"Hola mundo\"",
            "#   # https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html ",
            "#   sonido: \"ENTITY_PLAYER_LEVELUP 2.0 1.0\"",
            "#",
            "# ejemplo:",
            "#   barra de acción: \"Hola mundo\"",
            "#   # Categorías de sonido: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/SoundCategory.html",
            "#   sonido: \"ENTITY_PLAYER_LEVELUP WEATHER 2.0 1.0\" # Si quieres reproducir un sonido en una cierta categoría, por ejemplo si un jugador tiene la categoría de sonido \"WEATHER\" en la configuración del juego puesta a 0%, el sonido no se reproducirá.",
            "#",
            "# ejemplo:",
            "#   ocultarTítulo: true # Esto limpia el título de la pantalla, ejemplo si otro plugin envía título puedes limpiarlo con esto para cualquier acción",
            " "
    })
    @Description("# Esta sección es responsable de todos los mensajes usados durante la mala ejecución de un argumento de comando")
    public ENArgumentSection argument = new ENArgumentSection();

    @Getter
    @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        @Description("# {PERMISOS} - Permiso requerido")
        public Notice permissionMessage = Notice.chat("<red>✘ <dark_red>No tienes permiso para ejecutar este comando! <red>({PERMISOS})");

        @Description({" ", "# {USO} - Uso correcto"})
        public Notice usageMessage = Notice.chat("<gold>✘ <white>Uso correcto: <gold>{USO}");
        public Notice usageMessageHead = Notice.chat("<green>► <white>Uso correcto:");
        public Notice usageMessageEntry = Notice.chat("<green>► <white>{USO}");

        @Description(" ")
        public Notice offlinePlayer = Notice.chat("<red>✘ <dark_red>¡Este jugador está actualmente desconectado!");
        public Notice onlyPlayer = Notice.chat("<red>✘ <dark_red>¡El comando es solo para jugadores!");
        public Notice numberBiggerThanOrEqualZero = Notice.chat("<red>✘ <dark_red>¡El número debe ser mayor o igual a 0!");
        public Notice noItem = Notice.chat("<red>✘ <dark_red>¡Necesitas un objeto para usar este comando!");
        public Notice noMaterial = Notice.chat("<red>✘ <dark_red>¡Este objeto no existe!");
        public Notice noArgument = Notice.chat("<red>✘ <dark_red>¡Este argumento no existe!");
        public Notice noDamaged = Notice.chat("<red>✘ <dark_red>¡Este ítem no se puede reparar!");
        public Notice noDamagedItems = Notice.chat("<red>✘ <dark_red>¡Necesitas ítems dañados para usar este comando!");
        public Notice noEnchantment = Notice.chat("<red>✘ <dark_red>¡Este encantamiento no existe!");
        public Notice noValidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>¡Este nivel de encantamiento no es válido!");
        public Notice invalidTimeFormat = Notice.chat("<red>✘ <dark_red>¡Formato de tiempo inválido!");
        public Notice worldDoesntExist = Notice.chat("<red>✘ <dark_red>¡Este mundo no existe!");
        public Notice youMustGiveWorldName = Notice.chat("<red>✘ <dark_red>¡Debes proporcionar un nombre de mundo!");
        public Notice incorrectLocation = Notice.chat("<red>✘ <dark_red>¡Ubicación incorrecta!");
        public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>¡Número incorrecto de chunks!");
    }

    @Description({
            " ",
            "# Esta respuesta es responsable del formato general de algunos valores",
            "# El propósito de la sección es reducir la repetición de algunos mensajes."
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
            "# Esta sección es responsable de todos los mensajes usados por el jugador"
    })
    public ENTranslation.ENPlayerSection player = new ENTranslation.ENPlayerSection();

    @Getter
    @Contextual
    public static class ENPlayerSection implements PlayerSection {
        @Description({" ", "# {STATE} - Estado de vuelo"})
        public Notice flyEnable = Notice.chat("<green>► <white>El vuelo ahora está {STATE}");
        public Notice flyDisable = Notice.chat("<green>► <white>El vuelo ahora está {STATE}");

        @Description("# {PLAYER} - Jugador objetivo, {STATE} - Estado de vuelo del jugador objetivo")
        public Notice flySetEnable = Notice.chat("<green>► <white>El vuelo para <green>{PLAYER} <white>ahora está {STATE}");
        public Notice flySetDisable = Notice.chat("<green>► <white>El vuelo para <green>{PLAYER} <white>ahora está {STATE}");
    }
}