package dev.portero.xenon.database;

import dev.portero.xenon.configuration.implementation.PluginConfiguration;
import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import dev.portero.xenon.publish.Subscribe;
import dev.portero.xenon.publish.Subscriber;
import dev.portero.xenon.publish.event.XenonShutdownEvent;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

@BeanSetup
class DatabaseManagerSetup implements Subscriber {

    @Bean
    DatabaseManager databaseManager(PluginConfiguration pluginConfiguration, Logger logger, File dataFolder) {
        DatabaseManager databaseManager = new DatabaseManager(pluginConfiguration, logger, dataFolder);

        try {
            databaseManager.connect();
        } catch (SQLException exception) {
            logger.severe("Could not connect to database! Some functions may not work properly!");
            throw new RuntimeException(exception);
        }

        return databaseManager;
    }

    @Subscribe(XenonShutdownEvent.class)
    void onShutdown(DatabaseManager databaseManager) {
        databaseManager.close();
    }

}
