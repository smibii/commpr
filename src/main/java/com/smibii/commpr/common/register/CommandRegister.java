package com.smibii.commpr.common.register;

import com.mojang.brigadier.CommandDispatcher;
import com.smibii.commpr.COMMPR;
import com.smibii.commpr.common.commands.DebugPlayerActivityCommand;
import com.smibii.commpr.common.commands.GameMasterCommand;
import com.smibii.commpr.common.commands.LobbyCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = COMMPR.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegister {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        DebugPlayerActivityCommand.register(dispatcher);
        LobbyCommand.register(dispatcher);
        GameMasterCommand.register(dispatcher);
    }
}
