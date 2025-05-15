package com.smibii.commpr.player;

import com.smibii.commpr.config.ServerConfig;
import com.smibii.commpr.enums.PlayerActivity;
import net.minecraft.nbt.CompoundTag;

public class ComPlayer {
    private int lives = -1;
    private PlayerActivity activity = PlayerActivity.LOBBY;

    public int getLives() { return lives; }
    public void setLives(int lives) {
        if (lives == -1) this.lives = lives;

        int max = ServerConfig.MAX_LIVES.get();
        int min = ServerConfig.MIN_LIVES.get();

        this.lives = Math.max(min, Math.min(max, lives));
    }

    public void incrementLives() { setLives(lives++); }
    public void decrementLives() { setLives(lives--); }
    public void setInvulnerable() { setLives(-1); }

    public PlayerActivity getActivity() { return activity; }
    public void setActivity(PlayerActivity activity) { this.activity = activity; }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("lives", lives);
        nbt.putString("activity", activity.toString());
    }

    public void loadNBTData(CompoundTag nbt) {
        this.lives = nbt.getInt("lives");
        this.activity = PlayerActivity.fromString(nbt.getString("activity"));
    }
}
