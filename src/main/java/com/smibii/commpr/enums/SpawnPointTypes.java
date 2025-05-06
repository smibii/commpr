package com.smibii.commpr.enums;

public enum SpawnPointTypes {
    LOBBY("lobby"),
    GUNGAME("gunGame"),
    DEATHMATCH("deathmatch"),
    ZOMBIEp("zombie:player"),
    ZOMBIEz("zombie:zombie"),
    ZOMBIE_GUNGAMEp("zombieGunGame:player"),
    ZOMBIE_GUNGAMEz("zombieGunGame:zombie");

    private final String value;

    SpawnPointTypes(String value) {
        this.value = value;
    }

    private String getString() {
        return value;
    }

    public static SpawnPointTypes fromString(String value) {
        for (SpawnPointTypes type : SpawnPointTypes.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown mode: " + value);
    }
}
