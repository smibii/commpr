package com.smibii.commpr.client.events;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.common.player.ComPlayer;
import com.smibii.commpr.common.player.ComPlayerUtil;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.EnumSet;

@Mod.EventBusSubscriber(modid = COMMPR.MODID, value = Dist.CLIENT)
public class FirstPersonEnforcer {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || player.isDeadOrDying()) return;

        ComPlayer comPlayer = ComPlayerUtil.get(player);
        PlayerActivity activity = comPlayer.getActivity();

        if (EnumSet.of(
                PlayerActivity.INGAME,
                PlayerActivity.SPECTATOR,
                PlayerActivity.ELIMINATED,
                PlayerActivity.FREECAM
        ).contains(activity)) {
            mc.options.setCameraType(CameraType.FIRST_PERSON);
            mc.options.save();
        }
    }
}