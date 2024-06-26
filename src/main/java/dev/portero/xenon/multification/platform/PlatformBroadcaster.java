package dev.portero.xenon.multification.platform;

import dev.portero.xenon.multification.notice.NoticeContent;
import dev.portero.xenon.multification.notice.NoticePart;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;

public interface PlatformBroadcaster {

    <T extends NoticeContent>
    void announce(Audience viewer, NoticePart<T> notice);

    static PlatformBroadcaster withPlainSerializer() {
        return new PlatformBroadcasterImpl(new PlainComponentSerializer());
    }

    static PlatformBroadcaster withSerializer(ComponentSerializer<Component, Component, String> componentSerializer) {
        return new PlatformBroadcasterImpl(componentSerializer);
    }
}
