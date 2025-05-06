package com.smibii.commpr.events;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.config.ServerConfig;
import com.smibii.commpr.enums.PlayerActivity;
import com.smibii.commpr.interfaces.IServerPlayerMixin;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PlayerEvents {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            double x = ServerConfig.LOBBY_X.get();
            double y = ServerConfig.LOBBY_Y.get();
            double z = ServerConfig.LOBBY_Z.get();
            float yaw = ServerConfig.LOBBY_YAW.get().floatValue();
            float pitch = ServerConfig.LOBBY_PITCH.get().floatValue();
            player.teleportTo(player.serverLevel(), x, y, z, yaw, pitch);
        }
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
        LivingEntity entity = event.getEntity();

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
