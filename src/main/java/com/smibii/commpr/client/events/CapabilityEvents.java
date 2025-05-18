package com.smibii.commpr.client.events;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.common.player.ComPlayerProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = COMMPR.MODID)
public class CapabilityEvents {
    @SubscribeEvent
    public void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;
        event.addCapability(COMMPR.asResource("com_player"), new ComPlayerProvider());
    }
}
