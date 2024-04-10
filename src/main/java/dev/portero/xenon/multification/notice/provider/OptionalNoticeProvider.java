package dev.portero.xenon.multification.notice.provider;

import dev.portero.xenon.multification.notice.Notice;

import java.util.Optional;

@FunctionalInterface
public interface OptionalNoticeProvider<Translation> {

    Optional<Notice> extract(Translation translation);

}
