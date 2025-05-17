package com.smibii.commpr.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.smibii.commpr.config.ServerConfig;
import com.smibii.commpr.enums.PlayerActivity;
import com.smibii.commpr.player.ComPlayer;
import com.smibii.commpr.player.ComPlayerUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class LobbyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("lobby").requires((command) -> {
            return command.hasPermission(2);
        }).executes((command) -> {
            return execute(command.getSource());
        }));
    }

    private static int execute(CommandSourceStack command) {
        ServerPlayer player = command.getPlayer();

        assert player != null;
        ComPlayer comPlayer = ComPlayerUtil.get(player);
        comPlayer.setActivity(PlayerActivity.LOBBY);
        ComPlayerUtil.sync(player);

        double x = ServerConfig.LOBBY_X.get();
        double y = ServerConfig.LOBBY_Y.get();
        double z = ServerConfig.LOBBY_Z.get();
        float yaw = ServerConfig.LOBBY_YAW.get().floatValue();
        float pitch = ServerConfig.LOBBY_PITCH.get().floatValue();

        player.teleportTo(player.serverLevel(), x, y, z, yaw, pitch);

        command.sendSuccess(() -> Component.literal("Teleported to lobby"), false);
        return 1;
    }
}