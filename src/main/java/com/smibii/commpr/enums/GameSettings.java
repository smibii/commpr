package com.smibii.commpr.enums;

import com.smibii.commpr.game.Setting;

public enum GameSettings {
    GAMEMODE(new Setting<GameModeTypes>("gamemode", GameModeTypes.NONE));

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
