package com.smibii.commpr.common.tacz;

import com.tacz.guns.item.ModernKineticGunItem;

public class TacZLoadout {
    private final ItemStack primary = PrimaryGun.RIFLE.get(0).getValue();
    private final ItemStack secondary = SecondaryGun.PISTOL.get(0).getValue();
    
    public TacZLoadout() {
    }
}
