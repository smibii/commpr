package com.smibii.commpr.common.enums.tacz;

import com.smibii.commpr.common.tacz.TacZItemManager;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public enum SecondaryGun {
    RPG(TacZItemManager.RPG),
    SMG(TacZItemManager.SMG),
    PISTOL(TacZItemManager.PISTOL);

    private final List<ItemStack> value;

    SecondaryGun(List<ItemStack> value) {
        this.value = value;
    }

    public List<ItemStack> getList() {
        return value;
    }
}