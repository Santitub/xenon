package dev.portero.xenon.viewer;

import dev.portero.xenon.user.User;
import java.util.Collection;
import java.util.UUID;

public interface ViewerService {

    Collection<Viewer> all();

    Collection<Viewer> onlinePlayers();

    Viewer console();

    Viewer player(UUID uuid);

    Viewer user(User user);

    Viewer any(Object any);
}
