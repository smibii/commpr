package com.smibii.commpr.common.player;

import com.smibii.commpr.server.config.ServerConfig;
import com.smibii.commpr.server.database.DB;
import net.minecraft.server.level.ServerLevel;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public class ComPlayerLevel implements Serializable {
    private int currentLevel = 0;
    private double experience = 0.0;
    private double requiredExperience = 0.0;
    private double rxpMultiplier = 0.0;
    private boolean initialized = false;

    private double maxPlayerLevel;
    private double minXpGain;
    private double maxXpGain;

    public void initPlayerLevel(UUID playerId, ServerLevel level) throws IOException, ClassNotFoundException {
        DB<ComPlayerLevel> db = new DB<>("player_level", level);

        ComPlayerLevel loaded = db.get(playerId.toString());
        if (loaded != null) {
            this.currentLevel = loaded.currentLevel;
            this.experience = loaded.experience;
            this.requiredExperience = loaded.requiredExperience;
            this.rxpMultiplier = loaded.rxpMultiplier;
        } else {
            this.currentLevel = 0;
            this.experience = 0;
            this.requiredExperience = ServerConfig.START_RXP.get();
            this.rxpMultiplier = ServerConfig.RXP_MULTIPLIER.get();

            db.add(playerId.toString(), this);
        }

        this.maxPlayerLevel = ServerConfig.MAX_PLAYER_LEVEL.get();
        this.minXpGain = ServerConfig.MIN_XP_GAIN.get();
        this.maxXpGain = ServerConfig.MAX_XP_GAIN.get();

        this.initialized = true;
    }

    public boolean isInitialized() { return initialized; }

    public int getCurrentLevel() { return currentLevel; }
    public int getNextLevel() { return currentLevel + 1; }
    public double getExperience() { return experience; }
    public double getRequiredExperience() { return requiredExperience; }
    public double getExperienceNeeded() { return requiredExperience - experience; }

    public void setCurrentLevel(int level) { this.currentLevel = level; }
    public void setExperience(double xp) { this.experience = xp; }
    public void setRequiredExperience(double reqXp) { this.requiredExperience = reqXp; }


}
