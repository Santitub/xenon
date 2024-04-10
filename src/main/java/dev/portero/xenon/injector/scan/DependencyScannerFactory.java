package dev.portero.xenon.injector.scan;

import dev.portero.xenon.injector.DependencyInjector;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import dev.portero.xenon.injector.annotations.component.Component;
import dev.portero.xenon.injector.annotations.component.ConfigurationFile;
import dev.portero.xenon.injector.annotations.component.Controller;
import dev.portero.xenon.injector.annotations.component.Repository;
import dev.portero.xenon.injector.annotations.component.Service;
import dev.portero.xenon.injector.annotations.component.Task;
import dev.portero.xenon.injector.annotations.lite.LiteArgument;
import dev.portero.xenon.injector.annotations.lite.LiteCommandEditor;
import dev.portero.xenon.injector.annotations.lite.LiteContextual;
import dev.portero.xenon.injector.annotations.lite.LiteHandler;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.command.RootCommand;

public final class DependencyScannerFactory {

    private DependencyScannerFactory() {
    }

    public static DependencyScanner createDefault(DependencyInjector injector) {
        return new DependencyScanner(injector)
            .onAnnotations(
                Component.class,
                Service.class,
                Repository.class,
                Task.class,
                Controller.class,
                ConfigurationFile.class,
                BeanSetup.class,

                Command.class,
                RootCommand.class,
                LiteArgument.class,
                LiteHandler.class,
                LiteContextual.class,
                LiteCommandEditor.class
            );
    }


}
