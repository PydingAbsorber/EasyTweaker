package com.pyding.easy_tweaker.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class TweakerMenu extends AbstractContainerMenu {
    public TweakerMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenus.TWEAKER_MENU.get(), containerId);
        IItemHandler craftingSlots = new ItemStackHandler(10);
        int startX = 30;
        int startY = 17;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = startX + col * 18;
                int y = startY + row * 18;
                this.addSlot(new SlotItemHandler(craftingSlots, col + row * 3, x, y));
            }
        }
        this.addSlot(new SlotItemHandler(craftingSlots, 9, 124, 35));
        int invStartX = 8;
        int invStartY = 84;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, invStartX + col * 18, invStartY + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, invStartX + col * 18, invStartY + 58));
        }
    }

    public TweakerMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index >= 0 && index < 9) {
                if (!this.moveItemStackTo(itemstack1, 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            Inventory playerInventory = player.getInventory();
            for (int i = 0; i < 10; i++) {
                Slot slot = this.slots.get(i);
                if (slot != null && slot.hasItem()) {
                    ItemStack stackInSlot = slot.getItem();
                    if (!playerInventory.add(stackInSlot.copy())) {
                        player.drop(stackInSlot, false);
                    }
                    slot.set(ItemStack.EMPTY);
                }
            }
        }
    }

    public List<String> listAllItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Slot slot = this.slots.get(i);
            if (slot.hasItem()) {
                items.add(slot.getItem().getDescriptionId().replaceAll("\\.",":"));
            } else items.add("item:minecraft:air");
        }
        return items;
    }
}
