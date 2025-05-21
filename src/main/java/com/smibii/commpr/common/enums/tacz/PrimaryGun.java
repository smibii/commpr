package com.smibii.commpr.common.enums.tacz;

import com.smibii.commpr.common.tacz.TacZItemManager;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public enum PrimaryGun {
    SNIPER(TacZItemManager.SNIPER),
    RIFLE(TacZItemManager.RIFLE),
    SHOTGUN(TacZItemManager.SHOTGUN),
    MG(TacZItemManager.MG);

    private final List<ItemStack> value;

    PrimaryGun(List<ItemStack> value) {
        this.value = value;
    }

    public ItemStack get(int index) {
        return value.get(index);
    }
}
