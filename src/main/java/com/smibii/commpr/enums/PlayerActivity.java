package com.smibii.commpr.enums;

public enum PlayerActivity {
    LOBBY("lobby"),
    INGAME("ingame"),
    ELIMINATED("eliminated"),
    SPECTATOR("spectator"),
    GAMEMASTER("gamemaster");

    private final String value;

    PlayerActivity(String value) {
        this.value = value;
    }

    private String getString() {
        return value;
    }

    public static PlayerActivity fromString(String value) {
        for (PlayerActivity mode : PlayerActivity.values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown mode: " + value);
    }
}
