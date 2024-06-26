package dev.portero.xenon.notice;

import dev.portero.xenon.multification.notice.NoticeType;
import lombok.Getter;

@Getter
public enum NoticeTextType {

    CHAT(NoticeType.CHAT),
    ACTION_BAR(NoticeType.ACTION_BAR),
    TITLE(NoticeType.TITLE_WITH_EMPTY_SUBTITLE),
    SUBTITLE(NoticeType.SUBTITLE_WITH_EMPTY_TITLE);

    private final NoticeType type;

    NoticeTextType(NoticeType type) {
        this.type = type;
    }
}
