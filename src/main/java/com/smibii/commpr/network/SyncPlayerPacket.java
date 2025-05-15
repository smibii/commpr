package com.smibii.commpr.network;

import com.smibii.commpr.enums.PlayerActivity;
import com.smibii.commpr.player.ComPlayer;
import com.smibii.commpr.player.ComPlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPlayerPacket {
    private final int lives;
    private final PlayerActivity activity;

    public SyncPlayerPacket(int lives, PlayerActivity activity) {
        this.lives = lives;
        this.activity = activity;
    }

    public static void encode(SyncPlayerPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.lives);
        buf.writeUtf(msg.activity.toString());
    }

    public static SyncPlayerPacket decode(FriendlyByteBuf buf) {
        return new SyncPlayerPacket(buf.readInt(), PlayerActivity.fromString(buf.readUtf(32767)));
    }

    public static void handle(SyncPlayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;

            if (player == null) return;

            ComPlayer data = ComPlayerUtil.get(player);
            data.setLives(msg.lives);
            data.setActivity(msg.activity);
        });
        ctx.get().setPacketHandled(true);
    }
}
