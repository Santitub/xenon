package dev.portero.xenon.user;

import dev.portero.xenon.feature.language.Language;
import dev.portero.xenon.viewer.Viewer;

import java.util.Objects;
import java.util.UUID;

public class User implements Viewer {

    private final String name;
    private final UUID uuid;
    private UserClientSettings userClientSettings = UserClientSettings.NONE;
    private UserSettings userSettings = new UserSettingsImpl(() -> this.userClientSettings);

    User(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    @Override
    public Language getLanguage() {
        return this.userSettings.getLanguage();
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    public UserClientSettings getClientSettings() {
        return this.userClientSettings;
    }

    public void setClientSettings(UserClientSettings userClientSettings) {
        this.userClientSettings = userClientSettings;
    }

    public UserSettings getSettings() {
        return this.userSettings;
    }

    public void setSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        return this.name.equals(user.name) && this.uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.uuid);
    }
}

