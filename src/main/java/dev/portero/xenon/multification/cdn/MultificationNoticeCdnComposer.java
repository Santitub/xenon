package dev.portero.xenon.multification.cdn;

import dev.portero.xenon.multification.notice.Notice;
import dev.portero.xenon.multification.notice.NoticePart;
import dev.portero.xenon.multification.notice.NoticeType;
import dev.rollczi.litecommands.time.DurationParser;
import dev.rollczi.litecommands.time.TemporalAmountParser;
import net.dzikoysk.cdn.CdnSettings;
import net.dzikoysk.cdn.CdnUtils;
import net.dzikoysk.cdn.model.Element;
import net.dzikoysk.cdn.model.Entry;
import net.dzikoysk.cdn.model.Piece;
import net.dzikoysk.cdn.model.Section;
import net.dzikoysk.cdn.module.standard.StandardOperators;
import net.dzikoysk.cdn.reflect.TargetType;
import net.dzikoysk.cdn.serdes.Composer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.NotNull;
import panda.std.Result;

import java.time.Duration;
import java.util.List;

import static dev.portero.xenon.multification.notice.NoticeContent.Music;
import static dev.portero.xenon.multification.notice.NoticeContent.None;
import static dev.portero.xenon.multification.notice.NoticeContent.Text;
import static dev.portero.xenon.multification.notice.NoticeContent.Times;

public class MultificationNoticeCdnComposer implements Composer<Notice> {

    private static final String EMPTY_NOTICE = "[]";
    private static final String TIMES = "%s %s %s";
    private static final String MUSIC_WITH_CATEGORY = "%s %s %s %s";
    private static final String MUSIC_WITHOUT_CATEGORY = "%s %s %s";

    private static final TemporalAmountParser<Duration> DURATION_PARSER = DurationParser.TIME_UNITS;

    private static Element<?> empty(SerializeContext context) {
        return oneLine(context.key, context.description, EMPTY_NOTICE);
    }

    private static Element<?> oneLine(String key, List<String> description, String value) {
        return key == null || key.isEmpty() ? new Piece(value) : new Entry(description, key, value);
    }

    private static Section toSection(String key, List<String> description, List<String> elements) {
        Section section = new Section(description, key);

        for (String message : elements) {
            section.append(new Piece(StandardOperators.ARRAY + " " + CdnUtils.stringify(true, message)));
        }

        return section;
    }

    @Override
    public Result<? extends Element<?>, ? extends Exception> serialize(CdnSettings settings, List<String> description, String key, TargetType type, Notice entity) {
        SerializeContext context = new SerializeContext(settings, description, key, type, entity);

        return this.serializeEmpty(context)
            .orElse(error -> this.serializerUndisclosedChat(context))
            .orElse(error -> this.serializeAll(context));
    }

    private Result<Element<?>, Exception> serializeEmpty(SerializeContext context) {
        if (context.notice.parts().isEmpty()) {
            return Result.ok(empty(context));
        }

        return Result.error(new IllegalStateException("Notice is not empty"));
    }

    private Result<Element<?>, Exception> serializerUndisclosedChat(SerializeContext context) {
        List<NoticePart<?>> parts = context.notice.parts();

        if (parts.size() != 1) {
            return Result.error(new IllegalStateException("Notice is not one part"));
        }

        NoticePart<?> part = parts.get(0);

        if (part.type() != NoticeType.CHAT) {
            return Result.error(new IllegalStateException("Notice is not chat"));
        }

        if (!(part.content() instanceof Text text)) {
            return Result.error(new IllegalStateException("Notice is not text"));
        }

        List<String> messages = text.messages();

        if (messages.isEmpty()) {
            return Result.ok(empty(context));
        }

        if (messages.size() == 1) {
            return Result.ok(oneLine(context.key, context.description, messages.get(0)));
        }

        return Result.ok(toSection(context.key, context.description, messages));
    }

    private Result<Element<?>, Exception> serializeAll(SerializeContext context) {
        List<NoticePart<?>> parts = context.notice.parts();
        Section section = new Section(context.description, context.key);

        for (NoticePart<?> part : parts) {
            String key = part.type().getKey();

            if (part.content() instanceof Text text) {
                List<String> messages = text.messages();

                if (messages.isEmpty()) {
                    continue;
                }

                if (messages.size() == 1) {
                    section.append(oneLine(key, List.of(), messages.get(0)));
                    continue;
                }

                section.append(toSection(key, List.of(), messages));
                continue;
            }

            if (part.content() instanceof Times times) {
                String textTimes = TIMES.formatted(
                    DURATION_PARSER.format(times.fadeIn()),
                    DURATION_PARSER.format(times.stay()),
                    DURATION_PARSER.format(times.fadeOut())
                );

                section.append(new Entry(context.description, key, new Piece(textTimes)));
                continue;
            }

            if (part.content() instanceof None) {
                for (NoticeType type : NoticeType.values()) {
                    if (type != part.type()) {
                        continue;
                    }

                    section.append(new Entry(context.description, key, "true"));
                }

                continue;
            }

            if (part.content() instanceof Music music) {
                Entry entry = this.getSoundCategoryEntry(context, music, key);

                section.append(entry);
                continue;
            }

            return Result.error(new UnsupportedOperationException("Unsupported notice type: " + part.type()));
        }

        return Result.ok(section);
    }

    private @NotNull Entry getSoundCategoryEntry(SerializeContext context, Music music, String key) {
        SoundCategory category = music.category();

        return category == null
            ?
            new Entry(context.description, key, new Piece(MUSIC_WITHOUT_CATEGORY.formatted(
                music.sound().name(),
                String.valueOf(music.pitch()),
                String.valueOf(music.volume())
            )))
            :
            new Entry(context.description, key, new Piece(MUSIC_WITH_CATEGORY.formatted(
                music.sound().name(),
                category.name(),
                String.valueOf(music.pitch()),
                String.valueOf(music.volume())
            )));
    }

    @Override
    public Result<Notice, Exception> deserialize(CdnSettings settings, Element<?> source, TargetType type, Notice defaultValue, boolean entryAsRecord) {
        DeserializeContext context = new DeserializeContext(settings, source, type, defaultValue, entryAsRecord);

        return this.deserializeEmpty(context)
            .orElse(error -> this.deserializeAll(context));
    }

    private Result<Notice, Exception> deserializeEmpty(DeserializeContext context) {
        if (context.source() instanceof Piece piece && piece.getValue().equals(EMPTY_NOTICE)) {
            return Result.ok(Notice.empty());
        }

        if (context.source() instanceof Entry entry && entry.getPieceValue().equals(EMPTY_NOTICE)) {
            return Result.ok(Notice.empty());
        }

        return Result.error(new IllegalStateException("Notice is not empty"));
    }

    private Result<Notice, Exception> deserializeAll(DeserializeContext context) {
        if (context.source() instanceof Piece piece) {
            return Result.ok(Notice.chat(CdnUtils.destringify(piece.getValue())));
        }

        if (context.source() instanceof Entry entry) {
            return Result.ok(Notice.chat(CdnUtils.destringify(entry.getPieceValue())));
        }

        if (context.source() instanceof Section section) {
            return this.deserializeSection(section);
        }

        return Result.error(new UnsupportedOperationException("Unsupported element type: " + context.source()
            .getClass()));
    }

    private Result<Notice, Exception> deserializeSection(Section section) {
        Notice.Builder builder = Notice.builder();

        for (Element<?> element : section.getValue()) {
            if (element instanceof Piece piece) {
                builder.chat(this.deserializePiece(piece));
                continue;
            }

            if (element instanceof Entry entry) {
                String value = this.deserializePiece(entry.getValue());
                NoticeType noticeType = NoticeType.fromKey(entry.getName());

                if (noticeType.contentType() == Text.class) {
                    builder.withPart(new NoticePart<>(noticeType, new Text(List.of(value))));
                    continue;
                }

                if (noticeType.contentType() == Times.class) {
                    String[] times = value.split(" ");

                    if (times.length != 3) {
                        return Result.error(new IllegalStateException("Invalid times format"));
                    }

                    Duration fadeIn = DURATION_PARSER.parse(times[0]);
                    Duration stay = DURATION_PARSER.parse(times[1]);
                    Duration fadeOut = DURATION_PARSER.parse(times[2]);

                    builder.withPart(new NoticePart<>(noticeType, new Times(fadeIn, stay, fadeOut)));
                    continue;
                }

                if (noticeType.contentType() == Music.class) {
                    String[] music = value.split(" ");

                    if (music.length < 3 || music.length > 4) {
                        return Result.error(new IllegalStateException("Invalid music format"));
                    }

                    Sound sound = Sound.valueOf(music[0]);
                    SoundCategory category = music.length == 3 ? null : SoundCategory.valueOf(music[1]);
                    float pitch = Float.parseFloat(music[music.length - 2]);
                    float volume = Float.parseFloat(music[music.length - 1]);

                    builder.withPart(new NoticePart<>(noticeType, new Music(sound, category, pitch, volume)));
                    continue;
                }

                if (noticeType.contentType() == None.class) {
                    builder.withPart(new NoticePart<>(noticeType, None.INSTANCE));
                    continue;
                }

                return Result.error(new UnsupportedOperationException("Unsupported notice type: " + noticeType));
            }

            if (element instanceof Section subSection) {
                for (Element<?> subElement : subSection.getValue()) {
                    if (!(subElement instanceof Piece piece)) {
                        throw new IllegalStateException("Unsupported element type: " + subElement.getValue());
                    }

                    builder.chat(this.deserializePiece(piece));
                }

                continue;
            }

            return Result.error(new UnsupportedOperationException("Unsupported element type: " + element.getClass()));
        }

        return Result.ok(builder.build());
    }

    private String deserializePiece(Piece piece) {
        String value = piece.getValue();

        if (value.startsWith(StandardOperators.ARRAY)) {
            value = value.substring(StandardOperators.ARRAY.length());
        }

        return CdnUtils.destringify(value.trim());
    }

    private record SerializeContext(CdnSettings settings, List<String> description, String key, TargetType type,
                                    Notice notice) {
    }

    record DeserializeContext(CdnSettings settings, Element<?> source, TargetType type, Notice defaultValue,
                              boolean entryAsRecord) {
    }

}
