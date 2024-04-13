package dev.portero.xenon.multification.notice;

import static dev.portero.xenon.multification.notice.NoticeContent.Music;
import static dev.portero.xenon.multification.notice.NoticeContent.None;
import static dev.portero.xenon.multification.notice.NoticeContent.Text;
import static dev.portero.xenon.multification.notice.NoticeContent.Times;

public enum NoticeType {
    CHAT(Text.class, "chat"),
    ACTION_BAR(Text.class, "actionbar"),
    TITLE(Text.class, "title"),
    SUBTITLE(Text.class, "subtitle"),

    // TODO: Find a better solution, minecraft sends subtitle/title only when the second part also contains some part
    TITLE_WITH_EMPTY_SUBTITLE(Text.class, "titleWithEmptySubtitle"),
    SUBTITLE_WITH_EMPTY_TITLE(Text.class, "subtitleWithEmptyTitle"),
    TITLE_TIMES(Times.class, "times"),
    TITLE_HIDE(None.class, "titleHide"),
    SOUND(Music.class, "sound");

    private final Class<?> inputType;
    private final String name;

    NoticeType(Class<?> inputType, String name) {
        this.inputType = inputType;
        this.name = name;
    }

    public static NoticeType fromKey(String key) {
        for (NoticeType type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown notice type: " + key);
    }

    public String getKey() {
        return this.name;
    }

    public Class<?> contentType() {
        return this.inputType;
    }
}
