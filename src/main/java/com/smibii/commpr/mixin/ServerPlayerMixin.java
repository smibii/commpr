package com.smibii.commpr.mixin;

import com.smibii.commpr.config.ServerConfig;
import com.smibii.commpr.enums.PlayerActivity;
import com.smibii.commpr.interfaces.IServerPlayerMixin;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements IServerPlayerMixin {
    private final static int com$livesMax = ServerConfig.LIVES_MAX.get();
    private final static int com$livesMin = ServerConfig.LIVES_MIN.get();
    private int com$lives = -1;
    private PlayerActivity com$activity = PlayerActivity.LOBBY;

    @Override
    public void setLives(int lives) {
        if (lives <= com$livesMax && lives >= com$livesMin || lives == -1) {
            this.com$lives = lives;
        } else throw new IllegalArgumentException("Lives cant be above 10 or less than 0!");
    }

    @Override
    public void decrementLives() {
        if (!this.isDead()) {
            this.com$lives--;
        } else {
            this.com$lives = com$livesMin;
        }
    }

    // increment function

    @Override
    public boolean isDead() {
        return this.com$lives <= com$livesMin && this.com$lives != -1;
    }

    @Override
    public int getLives() {
        return com$lives;
    }

    @Override
    public void setActivity(PlayerActivity activity) {
        com$activity = activity;
    }

    @Override
    public PlayerActivity getActivity() {
        return com$activity;
    }
}
