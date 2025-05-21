package com.smibii.commpr.client.listeners;

import com.mojang.blaze3d.platform.InputConstants;
import com.smibii.commpr.COMMPR;
import com.smibii.commpr.client.overlay.InfoOverlayRenderer;
import com.smibii.commpr.common.enums.tacz.PrimaryGun;
import com.smibii.commpr.common.tacz.TacZItemManager;
import com.tacz.guns.client.input.AimKey;
import net.minecraft.client.KeyMapping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = COMMPR.MODID, value = Dist.CLIENT)
public class InfoOverlayEvents {
    private static final HashSet<UUID> handledDeaths = new HashSet<>();

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity target = event.getEntity();

        // -------------------- Makes it fire once!
        if (!handledDeaths.add(target.getUUID())) {
            handledDeaths.remove(target.getUUID());
            return;
        }

        DamageSource damageSource = event.getSource();
        if (!(damageSource.getEntity() instanceof ServerPlayer sourceEntity)) return;

        boolean midAir = !sourceEntity.onGround();

        ItemStack mainHandItem = sourceEntity.getMainHandItem();
        boolean noScope = !AimKey.AIM_KEY.isDown();

        if (noScope) {
            for (ItemStack sniper : TacZItemManager.SNIPER) {
                assert mainHandItem.getTag() != null;
                assert sniper.getTag() != null;
                String sniperId = sniper.getTag().getString("GunId");
                String mainHandId = mainHandItem.getTag().getString("GunId");
                noScope = sniperId.equals(mainHandId);
                if (noScope) break;
            }
        }

        InfoOverlayRenderer.kill(
                target,
                sourceEntity,
                midAir,
                noScope,
                damageSource
        );
    }
}
