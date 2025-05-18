package com.smibii.commpr.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.common.player.ComPlayer;
import com.smibii.commpr.common.player.ComPlayerUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class GameMasterCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gamemaster").requires((command) -> {
            return command.hasPermission(2);
        }).executes((command) -> {
            return execute(command.getSource());
        }));
    }

    private static int execute(CommandSourceStack command) {
        ServerPlayer player = command.getPlayer();

        assert player != null;
        ComPlayer comPlayer = ComPlayerUtil.get(player);
        PlayerActivity activity = comPlayer.getActivity();

        PlayerActivity newActivity = PlayerActivity.GAMEMASTER;
        boolean isGameMaster = activity.equals(newActivity);
        if (isGameMaster) newActivity = PlayerActivity.LOBBY;

        player.gameMode.changeGameModeForPlayer(GameType.CREATIVE);
        comPlayer.setActivity(newActivity);
        ComPlayerUtil.sync(player);
        command.sendSuccess(() -> Component.literal("GameMaster set to " + String.valueOf(isGameMaster)), false);
        return 1;
    }
}
