package com.smibii.commpr.server.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue MAX_LIVES;
    public static final ForgeConfigSpec.IntValue MIN_LIVES;

    public static final ForgeConfigSpec.DoubleValue LOBBY_X;
    public static final ForgeConfigSpec.DoubleValue LOBBY_Y;
    public static final ForgeConfigSpec.DoubleValue LOBBY_Z;
    public static final ForgeConfigSpec.DoubleValue LOBBY_YAW;
    public static final ForgeConfigSpec.DoubleValue LOBBY_PITCH;

    public static final ForgeConfigSpec.IntValue MAX_PLAYER_LEVEL;
    public static final ForgeConfigSpec.DoubleValue MIN_XP_GAIN;
    public static final ForgeConfigSpec.DoubleValue MAX_XP_GAIN;
    public static final ForgeConfigSpec.DoubleValue RXP_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue START_RXP;

    public static boolean isLoaded = false;

    static {
        BUILDER.push("Lives Settings");

        MAX_LIVES = BUILDER
                .comment("Maximum number of lives a player can have")
                .defineInRange("livesMax", 10, 1, 100);

        MIN_LIVES = BUILDER
                .comment("Minimum number of lives a player can have")
                .defineInRange("livesMin", 0, 0, 10);

        BUILDER.pop();
        BUILDER.push("Lobby Settings");

        LOBBY_X = BUILDER
                .comment("Lobby teleportation")
                .defineInRange("lobby_x", 0.5, -30000000.0, 30000000.0);

        LOBBY_Y = BUILDER
                .defineInRange("lobby_y", 100.0, -320.0, 320.0);

        LOBBY_Z = BUILDER
                .defineInRange("lobby_z", 0.5, -30000000.0, 30000000.0);

        LOBBY_YAW = BUILDER
                .defineInRange("lobby_yaw", 180.0, -180.0, 180.0);

        LOBBY_PITCH = BUILDER
                .defineInRange("lobby_pitch", 0.0, -90.0, 90.0);

        BUILDER.pop();
        BUILDER.push("Player settings");

        MAX_PLAYER_LEVEL = BUILDER
                .defineInRange("max_player_level", 255, 1, 5000);

        MIN_XP_GAIN = BUILDER
                .defineInRange("min_xp_gain", 10.0, 1.0, 500.0);

        MAX_XP_GAIN = BUILDER
                .defineInRange("max_xp_gain", 100.0, 1.0, 1000.0);

        RXP_MULTIPLIER = BUILDER
                .defineInRange("rxp_multiplier", 1.5, 1.1, 10.0);

        START_RXP = BUILDER
                .defineInRange("start_rxp", 100.0, 10.0, 500.0);

        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
