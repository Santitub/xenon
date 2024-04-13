package dev.portero.xenon;

import com.google.common.base.Stopwatch;
import dev.portero.xenon.configuration.ReloadableConfig;
import dev.portero.xenon.injector.DependencyInjector;
import dev.portero.xenon.injector.bean.BeanCandidate;
import dev.portero.xenon.injector.bean.BeanFactory;
import dev.portero.xenon.injector.bean.BeanHolder;
import dev.portero.xenon.injector.bean.LazyFieldBeanCandidate;
import dev.portero.xenon.injector.bean.processor.BeanProcessor;
import dev.portero.xenon.injector.bean.processor.BeanProcessorFactory;
import dev.portero.xenon.injector.scan.DependencyScanner;
import dev.portero.xenon.injector.scan.DependencyScannerFactory;
import dev.portero.xenon.publish.Publisher;
import dev.portero.xenon.publish.event.XenonInitializeEvent;
import dev.portero.xenon.publish.event.XenonShutdownEvent;
import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class XenonPlugin extends JavaPlugin {

    private final Stopwatch stopwatch = Stopwatch.createStarted();
    private Publisher publisher;

    @Override
    @SuppressWarnings("deprecation")
    public void onEnable() {
        BeanProcessor beanProcessor = BeanProcessorFactory.defaultProcessors(this);
        BeanFactory beanFactory = new BeanFactory(beanProcessor)
            .withCandidateSelf()
            .addCandidate(Plugin.class, () -> this)
            .addCandidate(Server.class, this::getServer)
            .addCandidate(Logger.class, this::getLogger)
            .addCandidate(PluginDescriptionFile.class, this::getDescription)
            .addCandidate(File.class, this::getDataFolder)
            .addCandidate(PluginManager.class, () -> this.getServer().getPluginManager());

        DependencyInjector dependencyInjector = new DependencyInjector(beanFactory);
        DependencyScanner scanner = DependencyScannerFactory.createDefault(dependencyInjector);

        beanFactory.addCandidate(DependencyInjector.class, () -> dependencyInjector);

        for (BeanCandidate beanCandidate : scanner.scan(this.getClass().getPackage())) {
            beanFactory.addCandidate(beanCandidate);
        }

        this.loadConfigContextual(beanFactory);

        beanFactory.initializeCandidates();

        this.publisher = beanFactory.getDependency(Publisher.class);

        this.initialize();
        this.publisher.publish(new XenonInitializeEvent());
    }

    public void disable() {
        this.publisher.publish(new XenonShutdownEvent());
    }

    private void loadConfigContextual(BeanFactory beanFactory) {
        List<BeanHolder<ReloadableConfig>> beans = beanFactory
            .initializeCandidates(ReloadableConfig.class)
            .getBeans(ReloadableConfig.class);

        for (BeanHolder<ReloadableConfig> bean : beans) {
            ReloadableConfig config = bean.get();

            for (Field field : config.getClass().getDeclaredFields()) {
                if (!field.getType().isAnnotationPresent(Contextual.class)) {
                    continue;
                }

                beanFactory.addCandidate(new LazyFieldBeanCandidate(config, field));
            }
        }
    }

    void initialize() {
        long millis = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("Successfully loaded Xenon in " + millis + "ms");
    }
}
