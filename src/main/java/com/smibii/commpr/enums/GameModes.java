package com.smibii.commpr.enums;

public enum GameModes {
    NONE("none"),
    GUNGAME("gunGame"),
    DEATHMATCH("deathmatch"),
    ZOMBIE("zombie"),
    ZOMBIE_GUNGAME("zombieGunGame");

    private final String value;

    GameModes(String value) {
        this.value = value;
    }

    private String getString() {
        return value;
    }

    public static GameModes fromString(String value) {
        for (GameModes mode : GameModes.values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown mode: " + value);
    }
}
