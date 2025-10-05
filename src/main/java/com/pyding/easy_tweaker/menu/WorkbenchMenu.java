package com.pyding.easy_tweaker.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchMenu extends TweakerMenu {

    public WorkbenchMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(10));
    }

    public WorkbenchMenu(int containerId, Inventory playerInventory, SimpleContainer container) {
        super(ModMenus.WORKBENCH_MENU.get(), containerId);
        int startX = 30;
        int startY = 17;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = startX + col * 18;
                int y = startY + row * 18;
                this.addSlot(new Slot(container, col + row * 3, x, y));
            }
        }
        this.addSlot(new Slot(container, 9, 124, 35));
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

    public WorkbenchMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, new SimpleContainer(10));
    }


    @Override
    public int getContainerSize() {
        return 10;
    }
}
