package com.smibii.commpr.enums;


import java.util.function.Consumer;

public enum GameModeTypes {
    GUNGAME("gunGame"),
    DEATHMATCH("deathmatch"),
    ZOMBIE("zombie"),
    ZOMBIE_GUNGAME("zombieGunGame");

    private final String value;

    GameModeTypes(String value) {
        this.value = value;
    }

    private String getString() {
        return value;
    }

    public static GameModeTypes fromString(String value) {
        for (GameModeTypes type : GameModeTypes.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + value);
    }

    public static void forEach(Consumer<GameModeTypes> callback) {
        for (GameModeTypes type : GameModeTypes.values()) {
            callback.accept(type);
        }
    }
}
