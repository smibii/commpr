package com.smibii.commpr.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue LIVES_MAX;
    public static final ForgeConfigSpec.IntValue LIVES_MIN;

    public static final ForgeConfigSpec.DoubleValue LOBBY_X;
    public static final ForgeConfigSpec.DoubleValue LOBBY_Y;
    public static final ForgeConfigSpec.DoubleValue LOBBY_Z;
    public static final ForgeConfigSpec.DoubleValue LOBBY_YAW;
    public static final ForgeConfigSpec.DoubleValue LOBBY_PITCH;

    static {
        BUILDER.push("Lives Settings");

        LIVES_MAX = BUILDER
                .comment("Maximum number of lives a player can have")
                .defineInRange("livesMax", 10, 1, 100);

        LIVES_MIN = BUILDER
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
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
