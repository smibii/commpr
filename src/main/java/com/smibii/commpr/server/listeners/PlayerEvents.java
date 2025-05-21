package com.smibii.commpr.server.listeners;

import com.smibii.commpr.common.tacz.TacZItemManager;
import com.smibii.commpr.server.config.ServerConfig;
import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.common.game.settings.GameSettingsUtil;
import com.smibii.commpr.common.player.ComPlayer;
import com.smibii.commpr.common.player.ComPlayerUtil;
import com.smibii.commpr.server.events.PlayerActivityChangeEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class PlayerEvents {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!(event.player instanceof ServerPlayer player)) return;

        if (player.isDeadOrDying()) return;

        ComPlayer comPlayer = ComPlayerUtil.get(player);
        PlayerActivity activity = comPlayer.getActivity();

        if (activity == PlayerActivity.LOBBY) {
            player.setHealth(player.getMaxHealth());
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(5.0f);
        }
    }

    @SubscribeEvent
    public void onPlayerActivityChange(PlayerActivityChangeEvent event) {
        if (event.player == null) return;

        ServerPlayer player = event.player;
        PlayerActivity activity = event.activity;

        if (activity == PlayerActivity.LOBBY) {
            player.setGameMode(GameType.ADVENTURE);
        }

        else if (activity == PlayerActivity.GAMEMASTER) {
            player.setGameMode(GameType.CREATIVE);
        }

        else if (EnumSet.of(PlayerActivity.ELIMINATED, PlayerActivity.FREECAM, PlayerActivity.SPECTATOR).contains(activity)) {
            player.setGameMode(GameType.SPECTATOR);
        }

        else {
            player.setGameMode(GameType.SURVIVAL);
        }
    }

    @SubscribeEvent
    public void onPlayerGameModeChange(PlayerEvent.PlayerChangeGameModeEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ComPlayer comPlayer = ComPlayerUtil.get(player);
        PlayerActivity activity = comPlayer.getActivity();

        if (activity.equals(PlayerActivity.LOBBY)) event.setNewGameMode(GameType.ADVENTURE);
        else if (activity.equals(PlayerActivity.INGAME)) event.setNewGameMode(GameType.SURVIVAL);
        else if (EnumSet.of(PlayerActivity.ELIMINATED, PlayerActivity.FREECAM, PlayerActivity.SPECTATOR).contains(activity)) {
            event.setNewGameMode(GameType.SPECTATOR);
        }
    }

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
            ComPlayerUtil.sync(player);
            ComPlayerUtil.syncAllToPlayer(player);
            comPlayer.setActivity(PlayerActivity.LOBBY);

            if (!player.level().isClientSide) GameSettingsUtil.syncPlayer(player);

            List<ItemStack> primary = TacZItemManager.SNIPER;
            if (primary.size() != 0) player.getInventory().add(primary.get(0));
        }
    }

    @SubscribeEvent
    public void onPlayerInteraction(PlayerInteractEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            BlockState state = event.getLevel().getBlockState(event.getPos());
            Block block = state.getBlock();

            if (block instanceof BedBlock) {
                Objects.requireNonNull(player.getServer()).executeIfPossible(() -> {
                    double x = ServerConfig.LOBBY_X.get();
                    double y = ServerConfig.LOBBY_Y.get();
                    double z = ServerConfig.LOBBY_Z.get();
                    float pitch = ServerConfig.LOBBY_PITCH.get().floatValue();

                    BlockPos pos = new BlockPos((int) x, (int) y, (int) z);

                    player.setRespawnPosition(Level.OVERWORLD, pos, pitch, true, false);
                });
            }

            if (player.isDeadOrDying()) return;

            ComPlayer comPlayer = ComPlayerUtil.get(player);
            PlayerActivity activity = comPlayer.getActivity();

            if (activity.equals(PlayerActivity.INGAME)) event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) ComPlayerUtil.sync(player);
    }

    @SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ComPlayer comPlayer = ComPlayerUtil.get(player);

        if (!comPlayer.getActivity().equals(PlayerActivity.INGAME)) event.setCanceled(true);
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
