package dev.portero.xenon.multification.adventure;

import net.kyori.adventure.audience.Audience;

public interface AudienceConverter<Viewer> {

    Audience convert(Viewer viewer);

}
