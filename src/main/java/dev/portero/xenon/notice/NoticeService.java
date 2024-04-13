package dev.portero.xenon.notice;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.component.Service;
import dev.portero.xenon.multification.Multification;
import dev.portero.xenon.multification.adventure.AudienceConverter;
import dev.portero.xenon.multification.executor.AsyncExecutor;
import dev.portero.xenon.multification.locale.LocaleProvider;
import dev.portero.xenon.multification.platform.PlatformBroadcaster;
import dev.portero.xenon.multification.shared.Replacer;
import dev.portero.xenon.multification.translation.TranslationProvider;
import dev.portero.xenon.multification.viewer.ViewerProvider;
import dev.portero.xenon.placeholder.PlaceholderRegistry;
import dev.portero.xenon.scheduler.Scheduler;
import dev.portero.xenon.translation.Translation;
import dev.portero.xenon.translation.TranslationManager;
import dev.portero.xenon.user.UserManager;
import dev.portero.xenon.viewer.BukkitViewerProvider;
import dev.portero.xenon.viewer.Viewer;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

@Service
public class NoticeService extends Multification<Viewer, Translation> {

    private final UserManager userManager;
    private final TranslationManager translationManager;
    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;
    private final PlaceholderRegistry registry;
    private final Server server;
    private final Scheduler scheduler;

    @Inject
    public NoticeService(
        UserManager userManager,
        TranslationManager translationManager,
        AudienceProvider audienceProvider,
        MiniMessage miniMessage,
        PlaceholderRegistry registry,
        Server server,
        Scheduler scheduler
    ) {
        this.userManager = userManager;
        this.translationManager = translationManager;
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
        this.registry = registry;
        this.server = server;
        this.scheduler = scheduler;
    }

    @Override
    public @NotNull ViewerProvider<Viewer> viewerProvider() {
        return new BukkitViewerProvider(this.userManager, this.server);
    }

    @Override
    public @NotNull TranslationProvider<Translation> translationProvider() {
        return this.translationManager;
    }

    @Override
    public @NotNull AudienceConverter<Viewer> audienceConverter() {
        return viewer -> {
            if (viewer.isConsole()) {
                return this.audienceProvider.console();
            }

            return this.audienceProvider.player(viewer.getUniqueId());
        };
    }

    @Override
    protected @NotNull PlatformBroadcaster platformBroadcaster() {
        return PlatformBroadcaster.withSerializer(this.miniMessage);
    }

    @Override
    protected @NotNull Replacer<Viewer> globalReplacer() {
        return (viewer, text) -> this.registry.format(text, viewer);
    }

    @Override
    protected @NotNull AsyncExecutor asyncExecutor() {
        return this.scheduler::async;
    }

    @Override
    protected @NotNull LocaleProvider<Viewer> localeProvider() {
        return viewer -> viewer.getLanguage().toLocale();
    }

    @Override
    public XenonBroadcastImpl<Viewer, Translation, ?> create() {
        return new XenonBroadcastImpl<>(
            this.asyncExecutor(),
            this.translationProvider(),
            this.viewerProvider(),
            this.platformBroadcaster(),
            this.localeProvider(),
            this.audienceConverter(),
            this.globalReplacer()
        );
    }
}
