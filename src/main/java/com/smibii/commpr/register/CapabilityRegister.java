package com.smibii.commpr.register;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.player.ComPlayerProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = COMMPR.MODID)
public class CapabilityRegister {
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(COMMPR.asResource("com_player"), new ComPlayerProvider());
        }
    }
}
