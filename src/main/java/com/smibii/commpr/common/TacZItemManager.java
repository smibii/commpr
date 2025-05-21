package com.smibii.commpr.common;

import com.tacz.guns.api.client.ModernKineticGunScriptAPI;
import com.tacz.guns.client.resource.pojo.attachment.AttachmentPojo;
import com.tacz.guns.client.resource.pojo.gun.GunPojo;
import com.tacz.guns.client.resource.utils.AttachmentDataUtils;
import com.tacz.guns.client.resource.utils.GunDataUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class TaczItemManager {

    public static final Map<GunType, List<String>> GUN = new EnumMap<>(GunType.class);
    public static final Map<AttachmentType, List<String>> ATTACHMENT = new EnumMap<>(AttachmentType.class);

    static {
        for (GunType type : GunType.values()) {
            GUN.put(type, new ArrayList<>());
        }
        for (AttachmentType type : AttachmentType.values()) {
            ATTACHMENT.put(type, new ArrayList<>());
        }

        loadGuns();
        loadAttachments();
    }

    private static void loadGuns() {
        for (String gunId : ModernKineticGunScriptAPI.getAllGunId()) {
            GunPojo gun = GunDataUtils.getGunPojo(gunId);
            if (gun == null) continue;

            try {
                GunType type = GunType.valueOf(gun.getGunType().toUpperCase());
                GUN.get(type).add(gunId);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void loadAttachments() {
        for (String attachmentId : AttachmentDataUtils.getAllAttachmentId()) {
            AttachmentPojo attachment = AttachmentDataUtils.getAttachmentPojo(attachmentId);
            if (attachment == null) continue;

            try {
                AttachmentType type = AttachmentType.valueOf(attachment.getType().toUpperCase());
                ATTACHMENT.get(type).add(attachmentId);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    @SafeVarargs
    public static ItemStack createItemStack(String gunId, Map<String, String>... attachments) {
        Item gunItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation("tacz", "modern_kinetic_gun"));
        if (!(gunItem instanceof ModernKineticGunItem)) return ItemStack.EMPTY;

        ItemStack stack = new ItemStack(gunItem);
        CompoundNBT tag = new CompoundNBT();
        tag.putString("GunId", gunId);
        stack.setTag(tag);

        if (attachments != null) {
            for (Map<String, String> map : attachments) {
                if (map == null) continue;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    attachAttachment(stack, entry.getKey(), entry.getValue());
                }
            }
        }

        return stack;
    }

    public static void attachAttachment(ItemStack gunStack, String slot, String attachmentId) {
        if (gunStack == null || gunStack.isEmpty()) return;

        CompoundNBT tag = gunStack.getOrCreateTag();

        CompoundNBT attachmentTag = new CompoundNBT();
        attachmentTag.putString("AttachmentId", attachmentId);

        CompoundNBT wrapper = new CompoundNBT();
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
        CompoundNBT tag = new CompoundNBT();
        tag.putString("AttachmentId", attachmentId);
        stack.setTag(tag);
        return stack;
    }
}
