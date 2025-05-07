package com.smibii.commpr.enums;

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

    public static PlayerActivity fromString(String value) {
        for (PlayerActivity activity : PlayerActivity.values()) {
            if (activity.value.equalsIgnoreCase(value)) {
                return activity;
            }
        }
        throw new IllegalArgumentException("Unknown activity: " + value);
    }
}
