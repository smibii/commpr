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
import net.nerdorg.minehop.config.MinehopConfig;

public class ToggleMovementCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("togglemovement").requires((command) -> {
            return command.hasPermission(2);
        }).executes((command) -> {
            return execute(command.getSource());
        }));
    }

    private static int execute(CommandSourceStack command) {
        boolean isEnabled = MinehopConfig.enabled;
        MinehopConfig.enabled = !isEnabled;
        command.sendSuccess(() -> Component.literal("Movement set to " + String.valueOf(!isEnabled)), false);
        return 1;
    }
}
