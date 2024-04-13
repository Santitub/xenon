package dev.portero.xenon.multification;

import dev.portero.xenon.multification.adventure.AudienceConverter;
import dev.portero.xenon.multification.executor.AsyncExecutor;
import dev.portero.xenon.multification.locale.LocaleProvider;
import dev.portero.xenon.multification.notice.NoticeBroadcast;
import dev.portero.xenon.multification.notice.NoticeBroadcastImpl;
import dev.portero.xenon.multification.notice.provider.NoticeProvider;
import dev.portero.xenon.multification.platform.PlatformBroadcaster;
import dev.portero.xenon.multification.shared.Formatter;
import dev.portero.xenon.multification.shared.Replacer;
import dev.portero.xenon.multification.translation.TranslationProvider;
import dev.portero.xenon.multification.viewer.ViewerProvider;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.UUID;

public abstract class Multification<Viewer, Translation> {

    public static final PlatformBroadcaster DEFAULT_BROADCASTER = PlatformBroadcaster.withPlainSerializer();
    public static final AsyncExecutor DEFAULT_EXECUTOR = runnable -> runnable.run();
    public static final Replacer<?> DEFAULT_REPLACER = (v, text) -> text;
    public static final LocaleProvider<?> LOCALE_PROVIDER = v -> Locale.ROOT;

    @CheckReturnValue
    public NoticeBroadcast<Viewer, Translation, ?> create() {
        return new NoticeBroadcastImpl<>(
            this.asyncExecutor(),
            this.translationProvider(),
            this.viewerProvider(),
            this.platformBroadcaster(),
            this.localeProvider(),
            this.audienceConverter(),
            this.globalReplacer()
        );
    }

    @NotNull
    protected abstract ViewerProvider<Viewer> viewerProvider();

    @NotNull
    protected abstract TranslationProvider<Translation> translationProvider();

    @NotNull
    protected abstract AudienceConverter<Viewer> audienceConverter();

    @NotNull
    protected PlatformBroadcaster platformBroadcaster() {
        return DEFAULT_BROADCASTER;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    protected Replacer<Viewer> globalReplacer() {
        return (Replacer<Viewer>) DEFAULT_REPLACER;
    }

    @NotNull
    protected AsyncExecutor asyncExecutor() {
        return DEFAULT_EXECUTOR;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    protected LocaleProvider<Viewer> localeProvider() {
        return (LocaleProvider<Viewer>) LOCALE_PROVIDER;
    }

    public void player(UUID player, NoticeProvider<Translation> extractor, Formatter... formatters) {
        this.create()
            .player(player)
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

    public void players(Iterable<UUID> players, NoticeProvider<Translation> extractor, Formatter... formatters) {
        this.create()
            .players(players)
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

    public void viewer(Viewer viewer, NoticeProvider<Translation> extractor, Formatter... formatters) {
        this.create()
            .viewer(viewer)
            .notice(extractor)
            .formatter(formatters)
            .send();
    }


    public void console(NoticeProvider<Translation> extractor, Formatter... formatters) {
        this.create()
            .console()
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

    public void all(NoticeProvider<Translation> extractor, Formatter... formatters) {
        this.create()
            .all()
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

}
