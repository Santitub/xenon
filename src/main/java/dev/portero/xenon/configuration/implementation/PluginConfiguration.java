package dev.portero.xenon.configuration.implementation;

import dev.portero.xenon.configuration.ReloadableConfig;
import dev.portero.xenon.database.DatabaseType;
import dev.portero.xenon.injector.annotations.component.ConfigurationFile;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

@ConfigurationFile
public class PluginConfiguration implements ReloadableConfig {

    @Description({
        "#",
        "# This is the main configuration file for Xenon.",
        "#",
    })

    @Description("# Whether the player should receive information about new plugin updates upon joining the server")
    public boolean shouldReceivePluginUpdates = true;

    @Description({" ", "# Database Section"})
    public Database database = new Database();

    @Contextual
    public static class Database {
        @Description({
            "# SQL Drivers and ports:",
            "# MySQL (3306), MariaDB (3306), PostgresQL (5432)",
            "# SQLite, H2"
        })
        public DatabaseType databaseType = DatabaseType.SQLITE;

        public String hostname = "127.0.0.1";
        public String database = "database";
        public String username = "root";
        public String password = "U5eStr0ngP4ssw0rd";
        public int port = 3306;
    }

    @Description({" ", "# Additional formatting options"})
    public Format format = new Format();

    @Contextual
    public static class Format {
        public String separator = "&7, ";
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }
}
