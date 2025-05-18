package com.smibii.commpr.common.network;

import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.common.player.ComPlayer;
import com.smibii.commpr.common.player.ComPlayerLevel;
import com.smibii.commpr.common.player.ComPlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPlayerPacket {
    private final int lives;
    private final PlayerActivity activity;
    private final int currentLevel;
    private final double experience;
    private final double requiredExperience;

    public SyncPlayerPacket(
            int lives,
            PlayerActivity activity,
            int currentLevel,
            double experience,
            double requiredExperience
    ) {
        this.lives = lives;
        this.activity = activity;
        this.currentLevel = currentLevel;
        this.experience = experience;
        this.requiredExperience = requiredExperience;
    }

    public static void encode(SyncPlayerPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.lives);
        buf.writeUtf(msg.activity.toString());
        buf.writeInt(msg.currentLevel);
        buf.writeDouble(msg.experience);
        buf.writeDouble(msg.requiredExperience);
    }

    public static SyncPlayerPacket decode(FriendlyByteBuf buf) {
        return new SyncPlayerPacket(
                buf.readInt(),
                PlayerActivity.fromString(buf.readUtf(32767)),
                buf.readInt(),
                buf.readDouble(),
                buf.readDouble()
        );
    }

    public static void handle(SyncPlayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft mc = Minecraft.getInstance();
                Player player = mc.player;
                if (player == null) return;

                ComPlayer data = ComPlayerUtil.get(player);
                data.setLives(msg.lives);
                data.setActivity(msg.activity);

                ComPlayerLevel playerLevel = data.getPlayerLevel();
                playerLevel.setCurrentLevel(msg.currentLevel);
                playerLevel.setExperience(msg.experience);
                playerLevel.setRequiredExperience(msg.requiredExperience);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
