package com.smibii.commpr.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.common.player.ComPlayer;
import com.smibii.commpr.common.player.ComPlayerLevel;
import com.smibii.commpr.common.player.ComPlayerUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class DebugPlayerActivityCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("activity").requires((command) -> {
            return command.hasPermission(2);
        }).then(Commands.argument("activityName", StringArgumentType.word()).executes((command) -> {
            return execute(command.getSource(), StringArgumentType.getString(command, "activityName"));
        })));
    }

    private static int execute(CommandSourceStack command, String value) {
        ServerPlayer player = command.getPlayer();

        PlayerActivity activity = PlayerActivity.fromString(value);
        if (activity == null) {
            command.sendFailure(Component.literal(value + " is not a known activity!"));
            return 0;
        }

        assert player != null;
        ComPlayer comPlayer = ComPlayerUtil.get(player);

        ComPlayerLevel playerLevel = comPlayer.getPlayerLevel();
        System.out.println(
                "Level: " + String.valueOf(playerLevel.getCurrentLevel()) +
                "\nExp: " + String.valueOf(playerLevel.getExperience()) +
                "\nRxp: " + String.valueOf(playerLevel.getRequiredExperience())
        );

        comPlayer.setActivity(activity);
        ComPlayerUtil.sync(player);
        command.sendSuccess(() -> Component.literal("Changed activity to " + value), false);
        return 1;
    }
}
