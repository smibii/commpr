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
    public static Map<ResourceLocation, ItemStack> SNIPER = new ArrayList<>();
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
    public static List<ResourceLocation> SIGHT = new ArrayList<>();
    public static List<ResourceLocation> AMMO = new ArrayList<>();
    public static List<ResourceLocation> BAYONET = new ArrayList<>();

    public static boolean isLoaded = false;

    static {
        loadGuns();
        loadAttachments();
        isLoaded = true;
    }

    private static void loadGuns() {
        ClientIndexManager.loadGunIndex();
        Set<Map.Entry<ResourceLocation, ClientGunIndex>> gunIndexSet = ClientIndexManager.getAllGuns();
        for (Map.Entry<ResourceLocation, ClientGunIndex> entry : gunIndexSet) {
            ClientGunIndex gunIndex = entry.getValue();
            ResourceLocation location = entry.getKey();

            String gunType = gunIndex.getType();
            FireMode fireMode = gunIndex.getGunData().getFireModeSet().get(0);
            int ammo = gunIndex.getGunData().getAmmoAmount() + 1;

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
        ClientIndexManager.loadAttachmentIndex();
        Set<Map.Entry<ResourceLocation, ClientAttachmentIndex>> attachmentIndexSet = ClientIndexManager.getAllAttachments();
        for (Map.Entry<ResourceLocation, ClientAttachmentIndex> entry : attachmentIndexSet) {
            ResourceLocation location = entry.getKey();
            String slot = getAttachmentSlot(location);

            switch (slot) {
                case "AttachmentSCOPE": SCOPE.add(location);
                case "AttachmentGTIP": GRIP.add(location);
                case "AttachmentMUZZLE": MUZZLE.add(location);
                case "AttachmentSTOCK": STOCK.add(location);
                case "AttachmentEXTENDED_MAG": EXTENDED_MAG.add(location);
                case "AttachmentLASER": LASER.add(location);
                case "AttachmentSIGHT": SIGHT.add(location);
                case "AttachmentAMMO": AMMO.add(location);
                case "AttachmentBAYONET": BAYONET.add(location);
            }
        }
    }

    public static ItemStack createItemStack(
            ResourceLocation location,
            FireMode fireMode,
            int ammo
    ) {
        return createItemStack(location, fireMode, ammo, null);
    }

    public static ItemStack createItemStack(
            ResourceLocation location,
            FireMode fireMode,
            int ammo,
            Map<String, ResourceLocation> attachments
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
            attachAttachments(stack, attachments);
        }

        return stack;
    }

    public static void attachAttachments(ItemStack gunStack, Map<String, ResourceLocation> attachments) {
        if (gunStack == null || gunStack.isEmpty()) return;

        CompoundTag tag = gunStack.getOrCreateTag();

        for (Map.Entry<String, ResourceLocation> entry : attachments.entrySet()) {
            String type = entry.getKey();
            ResourceLocation attachment = entry.getValue();

            CompoundTag attachmentTag = new CompoundTag();
            attachmentTag.putString("AttachmentId", attachment.toString());

            CompoundTag wrapper = new CompoundTag();
            wrapper.put("tag", attachmentTag);
            wrapper.putString("id", "tacz:attachment");
            wrapper.putByte("Count", (byte) 1);

            tag.put(type, wrapper);
        }

        gunStack.setTag(tag);
    }

    public static Map<String, ResourceLocation> createAttachments(ResourceLocation... locations) {
        Map<String, ResourceLocation> attachments = new HashMap<>();
        for (ResourceLocation location : locations) {
            String slot = getAttachmentSlot(location);
            attachments.put(slot, location);
        }
        return attachments;
    }

    private static String getAttachmentSlot(ResourceLocation location) {
        String path = location.getPath();
        if (path.contains("scope")) return "AttachmentSCOPE";
        if (path.contains("grip")) return "AttachmentGRIP";
        if (path.contains("muzzle")) return "AttachmentMUZZLE";
        if (path.contains("stock")) return "AttachmentSTOCK";
        if (path.contains("extended_mag")) return "AttachmentEXTENDED_MAG";
        if (path.contains("laser")) return "AttachmentLASER";
        if (path.contains("sight")) return "AttachmentSIGHT";
        if (path.contains("ammo")) return "AttachmentAMMO";
        if (path.contains("bayonet")) return "AttachmentBAYONET";
        return "unknown";
    }
}