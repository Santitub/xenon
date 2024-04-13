package dev.portero.xenon.user;

import java.util.Locale;

public interface UserClientSettings {

    UserClientSettings NONE = new UserClientNoneSettings();

    Locale getLocate();

    boolean isOnline();

    default boolean isOffline() {
        return !this.isOnline();
    }

}
