package com.smibii.commpr.events;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.enums.PlayerActivity;
import com.smibii.commpr.player.ComPlayer;
import com.smibii.commpr.player.ComPlayerUtil;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = COMMPR.MODID, value = Dist.CLIENT)
public class FirstPersonEnforcer {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        ComPlayer comPlayer = ComPlayerUtil.get(player);
        PlayerActivity activity = comPlayer.getActivity();

        if (activity != PlayerActivity.LOBBY) {
            mc.options.setCameraType(CameraType.FIRST_PERSON);
            mc.options.save();
        }
    }
}