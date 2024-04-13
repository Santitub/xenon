package dev.portero.xenon.event;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.component.Service;
import org.bukkit.Server;
import org.bukkit.event.Event;

@Service
public class EventCaller {

    private final Server server;

    @Inject
    public EventCaller(Server server) {
        this.server = server;
    }

    public <T extends Event> T callEvent(T event) {
        this.server.getPluginManager().callEvent(event);

        return event;
    }
}
