package com.smibii.commpr.enums;

import javax.annotation.Nullable;

public enum PlayerActivity {
    LOBBY("lobby"),
    INGAME("ingame"),
    ELIMINATED("eliminated"),
    SPECTATOR("spectator"),
    FREECAM("freecam"),
    GAMEMASTER("gamemaster");

    private final String value;

    PlayerActivity(String value) {
        this.value = value;
    }

    private String getString() {
        return value;
    }

    @Nullable
    public static PlayerActivity fromString(String value) {
        for (PlayerActivity activity : PlayerActivity.values()) {
            if (activity.value.equalsIgnoreCase(value)) {
                return activity;
            }
        }
        return null;
    }
}
