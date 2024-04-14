package dev.portero.xenon.multification.platform;

import dev.portero.xenon.multification.notice.NoticeContent;
import dev.portero.xenon.multification.notice.NoticeContent.Music;
import dev.portero.xenon.multification.notice.NoticeContent.Times;
import dev.portero.xenon.multification.notice.NoticePart;
import dev.portero.xenon.multification.notice.NoticeType;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.NamespacedKey;

import java.util.Map;
import java.util.function.BiConsumer;

import static dev.portero.xenon.multification.notice.NoticeType.ACTION_BAR;
import static dev.portero.xenon.multification.notice.NoticeType.CHAT;
import static dev.portero.xenon.multification.notice.NoticeType.SOUND;
import static dev.portero.xenon.multification.notice.NoticeType.SUBTITLE;
import static dev.portero.xenon.multification.notice.NoticeType.SUBTITLE_WITH_EMPTY_TITLE;
import static dev.portero.xenon.multification.notice.NoticeType.TITLE;
import static dev.portero.xenon.multification.notice.NoticeType.TITLE_HIDE;
import static dev.portero.xenon.multification.notice.NoticeType.TITLE_TIMES;
import static dev.portero.xenon.multification.notice.NoticeType.TITLE_WITH_EMPTY_SUBTITLE;

class PlatformBroadcasterImpl implements PlatformBroadcaster {

    private final ComponentSerializer<Component, Component, String> componentSerializer;
    private final Map<NoticeType, NoticePartAnnouncer<?>> announcers = Map.of(
            CHAT, this.text(Audience::sendMessage),
            ACTION_BAR, this.text(Audience::sendActionBar),
            TITLE, this.text((audience, title) -> {
                audience.sendTitlePart(TitlePart.SUBTITLE, Component.empty());
            }),
            SUBTITLE, this.text((audience, subtitle) -> {
                audience.sendTitlePart(TitlePart.SUBTITLE, subtitle);
            }),
            TITLE_WITH_EMPTY_SUBTITLE, this.text((audience, title) -> {
                audience.sendTitlePart(TitlePart.TITLE, title);
                audience.sendTitlePart(TitlePart.SUBTITLE, Component.empty());
            }),
            SUBTITLE_WITH_EMPTY_TITLE, this.text((audience, subtitle) -> {
                audience.sendTitlePart(TitlePart.TITLE, Component.empty());
                audience.sendTitlePart(TitlePart.SUBTITLE, subtitle);
            }),
            TITLE_TIMES, new TimesNoticePartAnnouncer(),
            TITLE_HIDE, (audience, input) -> audience.clearTitle(),
            SOUND, new SoundNoticePartAnnouncer()
    );

    public PlatformBroadcasterImpl(ComponentSerializer<Component, Component, String> componentSerializer) {
        this.componentSerializer = componentSerializer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NoticeContent> void announce(Audience audience, NoticePart<T> part) {
        NoticePartAnnouncer<T> announcer = (NoticePartAnnouncer<T>) this.announcers.get(part.type());

        if (announcer == null) {
            throw new IllegalStateException("No announcer for " + part.type());
        }

        announcer.announce(audience, part.content());
    }

    private NoticePartAnnouncer<NoticeContent.Text> text(BiConsumer<Audience, Component> consumer) {
        return (audience, input) -> {
            for (String text : input.messages()) {
                consumer.accept(audience, this.componentSerializer.deserialize(text));
            }
        };
    }

    interface NoticePartAnnouncer<T extends NoticeContent> {
        void announce(Audience audience, T input);
    }

    static class TimesNoticePartAnnouncer implements NoticePartAnnouncer<Times> {
        @Override
        public void announce(Audience audience, Times timed) {
            Title.Times times = Title.Times.times(
                    timed.fadeIn(),
                    timed.stay(),
                    timed.fadeOut()
            );

            audience.sendTitlePart(TitlePart.TIMES, times);
        }
    }

    static class SoundNoticePartAnnouncer implements NoticePartAnnouncer<Music> {
        @Override
        public void announce(Audience audience, Music music) {
            NamespacedKey soundKey = music.sound().getKey();

            Sound sound = music.category() != null
                    ? Sound.sound(soundKey, Sound.Source.valueOf(music.category()
                                                                         .name()), music.volume(), music.pitch())
                    : Sound.sound(soundKey, Sound.Source.MASTER, music.volume(), music.pitch());

            audience.playSound(sound);
        }
    }
}
