package com.smibii.commpr.common.enums.tacz;

import com.smibii.commpr.common.tacz.TacZItemManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public enum Attachment {
    SCOPE(TacZItemManager.SCOPE),
    GRIP(TacZItemManager.GRIP),
    MUZZLE(TacZItemManager.MUZZLE),
    STOCK(TacZItemManager.STOCK),
    EXTENDED_MAG(TacZItemManager.EXTENDED_MAG),
    LASER(TacZItemManager.LASER),
    SIGHT(TacZItemManager.SIGHT),
    AMMO(TacZItemManager.AMMO),
    BAYONET(TacZItemManager.BAYONET);

    private final List<ResourceLocation> value;

    Attachment(List<ResourceLocation> value) {
        this.value = value;
    }

    public Map<String, ResourceLocation> getMap(int index) {
        return TacZItemManager.createAttachments(value.get(index));
    }

    public ResourceLocation get(int index) {
        return value.get(index);
    }
}