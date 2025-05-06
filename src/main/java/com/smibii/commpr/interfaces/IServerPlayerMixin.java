package com.smibii.commpr.interfaces;

import com.smibii.commpr.enums.PlayerActivity;

public interface IServerPlayerMixin {
    void setLives(int lives);
    void decrementLives();
    boolean isDead();
    int getLives();
    void setActivity(PlayerActivity activity);
    PlayerActivity getActivity();
}
