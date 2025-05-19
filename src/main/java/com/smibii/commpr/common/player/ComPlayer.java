package com.smibii.commpr.common.player;

import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.server.config.ServerConfig;
import com.smibii.commpr.server.events.PlayerActivityChangeEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;

public class ComPlayer {
    private int lives = -1;
    private PlayerActivity activity = PlayerActivity.LOBBY;
    private final ComPlayerLevel playerLevel = new ComPlayerLevel();
    private ServerPlayer player;

    public void initPlayerLevel(ServerPlayer player) {
        if (playerLevel.isInitialized()) return;

        try {
            playerLevel.initPlayerLevel(player.getUUID(), (ServerLevel) player.level());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.player = player;
    }

    public ComPlayerLevel getPlayerLevel() { return playerLevel; }

    public int getLives() { return lives; }
    public void setLives(int lives) {
        if (lives == -1) this.lives = lives;

        int max = ServerConfig.MAX_LIVES.get();
        int min = ServerConfig.MIN_LIVES.get();

        this.lives = Math.max(min, Math.min(max, lives));
    }

    public void incrementLives() { setLives(lives + 1); }
    public void decrementLives() { setLives(lives - 1); }
    public void setInvulnerable() { setLives(-1); }

    public PlayerActivity getActivity() { return activity; }
    public void setActivity(PlayerActivity activity) {
        this.activity = activity;
        MinecraftForge.EVENT_BUS.post(new PlayerActivityChangeEvent(this, player));
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("lives", lives);
        nbt.putString("activity", activity.toString());
        nbt.putInt("level", playerLevel.getCurrentLevel());
        nbt.putDouble("exp", playerLevel.getExperience());
        nbt.putDouble("rxp", playerLevel.getRequiredExperience());
    }

    public void loadNBTData(CompoundTag nbt) {
        this.lives = nbt.getInt("lives");
        this.activity = PlayerActivity.fromString(nbt.getString("activity"));
        this.playerLevel.setCurrentLevel(nbt.getInt("level"));
        this.playerLevel.setExperience(nbt.getInt("exp"));
        this.playerLevel.setRequiredExperience(nbt.getInt("rxp"));
    }
}