package dev.portero.xenon.multification.notice.provider;

import dev.portero.xenon.multification.notice.Notice;

@FunctionalInterface
public interface NoticeProvider<Translation> {

    Notice extract(Translation translation);

}