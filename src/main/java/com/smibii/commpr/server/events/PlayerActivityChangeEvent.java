package com.smibii.commpr.server.events;

import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.common.player.ComPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class PlayerActivityChangeEvent extends Event {
    public final ComPlayer comPlayer;
    public final PlayerActivity activity;
    public final ServerPlayer player;

    public PlayerActivityChangeEvent(ComPlayer comPlayer, ServerPlayer player) {
        this.comPlayer = comPlayer;
        this.activity = comPlayer.getActivity();
        this.player = player;
    }
}
