package com.smibii.commpr.common.tacz;

import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.resource.ClientIndexManager;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class TacZItemManager {
    // Primary
    public static List<ItemStack> SNIPER = new ArrayList<>();
    public static List<ItemStack> RIFLE = new ArrayList<>();
    public static List<ItemStack> SHOTGUN = new ArrayList<>();
    public static List<ItemStack> MG = new ArrayList<>();

    // Secondary
    public static List<ItemStack> RPG = new ArrayList<>();
    public static List<ItemStack> SMG = new ArrayList<>();
    public static List<ItemStack> PISTOL = new ArrayList<>();

    // Attachments
    public static List<ResourceLocation> SCOPE = new ArrayList<>();
    public static List<ResourceLocation> GRIP = new ArrayList<>();
    public static List<ResourceLocation> MUZZLE = new ArrayList<>();
    public static List<ResourceLocation> STOCK = new ArrayList<>();
    public static List<ResourceLocation> EXTENDED_MAG = new ArrayList<>();
    public static List<ResourceLocation> LASER = new ArrayList<>();

    public static boolean isLoaded = false;

    static {
        System.out.println("LOADING ----------------------------------------------dawdWDAWDwdawdAWDwdawdawdWDAD");
        loadGuns();
        loadAttachments();
    }

    private static void loadGuns() {
        ClientIndexManager.loadGunIndex();
        Set<Map.Entry<ResourceLocation, ClientGunIndex>> gunIndexSet = ClientIndexManager.getAllGuns();
        for (Map.Entry<ResourceLocation, ClientGunIndex> entry : gunIndexSet) {
            ClientGunIndex gunIndex = entry.getValue();
            ResourceLocation location = entry.getKey();

            String gunType = gunIndex.getType();
            FireMode fireMode = gunIndex.getGunData().getFireModeSet().get(0);
            int ammo = gunIndex.getGunData().getAmmoAmount();

            ItemStack item = createItemStack(location, fireMode, ammo);

            switch (gunType) {
                case "sniper": SNIPER.add(item);
                case "rifle": RIFLE.add(item);
                case "shotgun": SHOTGUN.add(item);
                case "mg": MG.add(item);
                case "rpg": RPG.add(item);
                case "smg": SMG.add(item);
                case "pistol": PISTOL.add(item);
            }
        }
    }

    private static void loadAttachments() {
        ClientIndexManager.loadGunIndex();
        Set<Map.Entry<ResourceLocation, ClientAttachmentIndex>> attachmentIndexSet = ClientIndexManager.getAllAttachments();
        System.out.println(attachmentIndexSet);
        for (Map.Entry<ResourceLocation, ClientAttachmentIndex> entry : attachmentIndexSet) {
            ClientAttachmentIndex attachmentIndex = entry.getValue();
            ResourceLocation location = entry.getKey();

            System.out.println(attachmentIndex.getSlotTexture());

            if (attachmentIndex.isScope()) SCOPE.add(location);
        }
    }

    @SafeVarargs
    public static ItemStack createItemStack(
            ResourceLocation location,
            FireMode fireMode,
            int ammo,
            Map<String, ResourceLocation>... attachments
    ) {
        Item gunItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation("tacz", "modern_kinetic_gun"));
        if (!(gunItem instanceof ModernKineticGunItem)) return ItemStack.EMPTY;

        ItemStack stack = new ItemStack(gunItem);
        CompoundTag tag = new CompoundTag();
        tag.putString("GunId", location.toString());
        tag.putString("GunFireMode", fireMode.name());
        tag.putInt("GunCurrentAmmoCount", ammo);
        stack.setTag(tag);

        if (attachments != null) {
            for (Map<String, ResourceLocation> map : attachments) {
                if (map == null) continue;
                for (Map.Entry<String, ResourceLocation> entry : map.entrySet()) {
                    attachAttachment(stack, entry.getKey(), entry.getValue());
                }
            }
        }

        return stack;
    }

    public static void attachAttachment(ItemStack gunStack, String slot, ResourceLocation attachmentLocation) {
        if (gunStack == null || gunStack.isEmpty()) return;

        CompoundTag tag = gunStack.getOrCreateTag();

        CompoundTag attachmentTag = new CompoundTag();
        attachmentTag.putString("AttachmentId", attachmentLocation.toString());

        CompoundTag wrapper = new CompoundTag();
        wrapper.put("tag", attachmentTag);
        wrapper.putString("id", "tacz:attachment");
        wrapper.putByte("Count", (byte) 1);

        tag.put(slot, wrapper);
        gunStack.setTag(tag);
    }

    public static ItemStack createAttachment(String attachmentId) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("tacz", "attachment"));
        if (item == null) return ItemStack.EMPTY;

        ItemStack stack = new ItemStack(item);
        CompoundTag tag = new CompoundTag();
        tag.putString("AttachmentId", attachmentId);
        stack.setTag(tag);
        return stack;
    }
}