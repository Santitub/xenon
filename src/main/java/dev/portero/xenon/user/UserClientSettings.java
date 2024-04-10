package dev.portero.xenon.user;

import java.util.Locale;

public interface UserClientSettings {

    Locale getLocate();

    UserClientSettings NONE = new UserClientNoneSettings();

    boolean isOnline();

    default boolean isOffline() {
        return !this.isOnline();
    }

}
