package com.smibii.commpr.common.game.settings;

import com.smibii.commpr.common.game.modes.GameModeTypes;

public enum GameSettings {
    GAMEMODE(new Setting<GameModeTypes>("gamemode", GameModeTypes.NONE)),
    RUNNING(new Setting<Boolean>("running", false));

    private final Setting<?> setting;

    <T> GameSettings(Setting<T> setting) {
        this.setting = setting;
    }

    public Setting<?> getSetting() {
        return setting;
    }

    @SuppressWarnings("unchecked")
    public <T> Setting<T> getSettingTyped() {
        return (Setting<T>) setting;
    }
}
