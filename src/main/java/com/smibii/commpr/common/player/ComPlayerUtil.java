package com.smibii.commpr.common.player;

import com.smibii.commpr.common.network.NetworkHandler;
import com.smibii.commpr.common.network.SyncPlayerPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

public class ComPlayerUtil {
    public static ComPlayer get(Player player) {
        return player.getCapability(ComPlayerProvider.COM_PLAYER)
                .orElseThrow(() -> new IllegalStateException("ComPlayer missing!"));
    }

    public static void sync(ServerPlayer player) {
        ComPlayer data = ComPlayerUtil.get(player);
        data.initPlayerLevel(player);
        ComPlayerLevel playerLevel = data.getPlayerLevel();

        NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new SyncPlayerPacket(
                        data.getLives(),
                        data.getActivity(),
                        playerLevel.getCurrentLevel(),
                        playerLevel.getExperience(),
                        playerLevel.getRequiredExperience()
                ));
    }

    public static void syncAllToPlayer(ServerPlayer serverPlayer) {
        MinecraftServer server = serverPlayer.getServer();
        if (server == null) return;
        List<ServerPlayer> playerList = server.getPlayerList().getPlayers();

        playerList.forEach((player) -> {
            if (player.isDeadOrDying()) return;
            ComPlayer data = ComPlayerUtil.get(player);
            data.initPlayerLevel(player);
            ComPlayerLevel playerLevel = data.getPlayerLevel();

            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
                    new SyncPlayerPacket(
                            data.getLives(),
                            data.getActivity(),
                            playerLevel.getCurrentLevel(),
                            playerLevel.getExperience(),
                            playerLevel.getRequiredExperience()
                    ));
        });
    }
}
