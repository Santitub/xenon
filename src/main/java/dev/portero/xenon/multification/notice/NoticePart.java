package dev.portero.xenon.multification.notice;

public record NoticePart<T extends NoticeContent>(NoticeType type, T content) {

    public NoticePart(NoticeType type, T content) {
        this.type = type;
        this.content = content;
    }

    public NoticeType type() {
        return this.type;
    }

    public T content() {
        return this.content;
    }

}
