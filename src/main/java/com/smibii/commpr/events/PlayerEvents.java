package com.smibii.commpr.events;

import com.smibii.commpr.config.ServerConfig;
import com.smibii.commpr.enums.PlayerActivity;
import com.smibii.commpr.player.ComPlayer;
import com.smibii.commpr.player.ComPlayerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEvents {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            double x = ServerConfig.LOBBY_X.get();
            double y = ServerConfig.LOBBY_Y.get();
            double z = ServerConfig.LOBBY_Z.get();

            BlockPos pos = new BlockPos((int) x, (int) y, (int) z);

            float yaw = ServerConfig.LOBBY_YAW.get().floatValue();
            float pitch = ServerConfig.LOBBY_PITCH.get().floatValue();

            player.teleportTo(player.serverLevel(), x, y, z, yaw, pitch);
            player.setRespawnPosition(Level.OVERWORLD, pos, pitch, true, false);

            ComPlayer comPlayer = ComPlayerUtil.get(player);
            comPlayer.setInvulnerable();
            comPlayer.setActivity(PlayerActivity.LOBBY);
            ComPlayerUtil.sync(player);
        }
    }

    @SubscribeEvent
    public void onPlayerSetRespawn(PlayerSetSpawnEvent event) {
        double x = ServerConfig.LOBBY_X.get();
        double y = ServerConfig.LOBBY_Y.get();
        double z = ServerConfig.LOBBY_Z.get();

        BlockPos pos = new BlockPos((int) x, (int) y, (int) z);
        float pitch = ServerConfig.LOBBY_PITCH.get().floatValue();

        if (event.getEntity() instanceof ServerPlayer player) ComPlayerUtil.sync(player);

        // ServerPlayer player = (ServerPlayer) event.getEntity();
        // player.setRespawnPosition(Level.OVERWORLD, pos, pitch, true, false);
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) ComPlayerUtil.sync(player);
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        // Check if in game
        // if (event.getEntity() instanceof ServerPlayer player) {
        //     String uuid = player.getStringUUID();

        //     if (player_list.containsEntity(uuid)) {
        //         player_list.removeEntity(uuid);
        //     }
        // }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        // if (player_list.containsEntity(uuid)) {
            // ServerPlayer player = player_list.getEntity(uuid);

            // assert player != null;
            // if (player.lives > 0 || player.lives == -1) {
                // Respawn in game
            // } else {
            // player.activity = COM_Player_Activty.LOBBY;
                // Respawn in lobby
            // }
        // }
    }
}
