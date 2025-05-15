package com.smibii.commpr.player;

import com.smibii.commpr.network.NetworkHandler;
import com.smibii.commpr.network.SyncPlayerPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class ComPlayerUtil {
    public static ComPlayer get(Player player) {
        return player.getCapability(ComPlayerProvider.COM_PLAYER)
                .orElseThrow(() -> new IllegalStateException("ComPlayer missing!"));
    }
    public static void sync(ServerPlayer player) {
        ComPlayer data = ComPlayerUtil.get(player);
        NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new SyncPlayerPacket(data.getLives(), data.getActivity()));
    }
}
