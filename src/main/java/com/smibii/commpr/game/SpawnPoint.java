package com.smibii.commpr.game;

import com.smibii.commpr.enums.GameModes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;

import java.util.*;

public class SpawnPoint {
    private final ServerLevel level;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    public ArrayList<GameModes> modesList;

    public SpawnPoint(ServerLevel level, double x, double y, double z, float yaw, float pitch, ArrayList<GameModes> modesList) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.modesList = modesList;
    }

    public void spawnEntity(LivingEntity entity) {
        Set<RelativeMovement> relative = EnumSet.noneOf(RelativeMovement.class);
        entity.teleportTo(level, x, y, z, relative, yaw, pitch);
    }
}
