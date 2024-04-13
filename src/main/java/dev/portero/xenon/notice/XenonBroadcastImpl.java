package dev.portero.xenon.notice;

import dev.portero.xenon.multification.adventure.AudienceConverter;
import dev.portero.xenon.multification.executor.AsyncExecutor;
import dev.portero.xenon.multification.locale.LocaleProvider;
import dev.portero.xenon.multification.notice.Notice;
import dev.portero.xenon.multification.notice.NoticeBroadcastImpl;
import dev.portero.xenon.multification.notice.NoticeContent;
import dev.portero.xenon.multification.notice.NoticeType;
import dev.portero.xenon.multification.notice.provider.TextMessageProvider;
import dev.portero.xenon.multification.platform.PlatformBroadcaster;
import dev.portero.xenon.multification.shared.Replacer;
import dev.portero.xenon.multification.translation.TranslationProvider;
import dev.portero.xenon.multification.viewer.ViewerProvider;
import dev.portero.xenon.placeholder.Placeholders;
import dev.portero.xenon.user.User;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * This class is an extension of {@link NoticeBroadcastImpl} that provides more methods for creating notices.
 */
public class XenonBroadcastImpl<Viewer, Translation, B extends XenonBroadcastImpl<Viewer, Translation, B>>
    extends NoticeBroadcastImpl<Viewer, Translation, B> {

    public XenonBroadcastImpl(
        AsyncExecutor asyncExecutor,
        TranslationProvider<Translation> translationProvider,
        ViewerProvider<Viewer> viewerProvider,
        PlatformBroadcaster platformBroadcaster,
        LocaleProvider<Viewer> localeProvider,
        AudienceConverter<Viewer> audienceConverter,
        Replacer<Viewer> replacer
    ) {
        super(
            asyncExecutor,
            translationProvider,
            viewerProvider,
            platformBroadcaster,
            localeProvider,
            audienceConverter,
            replacer
        );
    }

    public <CONTEXT> B placeholders(Placeholders<CONTEXT> placeholders, CONTEXT context) {
        return this.formatter(placeholders.toFormatter(context));
    }

    public B notice(NoticeTextType type, TextMessageProvider<Translation> extractor) {
        this.notifications.add(translation -> {
            List<String> list = Collections.singletonList(extractor.extract(translation));
            NoticeContent.Text content = new NoticeContent.Text(list);

            NoticeType noticeType = type.getType();
            return Notice.of(noticeType, content);
        });

        return this.getThis();
    }

    public B notice(NoticeTextType type, String message) {
        this.notifications.add(translation -> {
            List<String> list = Collections.singletonList(message);
            NoticeContent.Text content = new NoticeContent.Text(list);

            NoticeType noticeType = type.getType();
            return Notice.of(noticeType, content);
        });

        return this.getThis();
    }

    public B messages(Function<Translation, List<String>> messages) {
        this.notifications.add(translation -> {
            List<String> list = messages.apply(translation);
            NoticeContent.Text content = new NoticeContent.Text(list);

            return Notice.of(NoticeType.CHAT, content);
        });

        return this.getThis();
    }

    public B user(User user) {
        return this.player(user.getUniqueId());
    }
}
