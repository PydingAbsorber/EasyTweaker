package com.pyding.easy_tweaker.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class BrewingMenu extends TweakerMenu {

    public BrewingMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(5));
    }

    public BrewingMenu(int id, Inventory playerInventory, SimpleContainer container) {
        super(ModMenus.BREWING_MENU.get(), id);
        this.addSlot(new Slot(container, 0, 79, 17));
        this.addSlot(new Slot(container, 1, 56, 51));
        this.addSlot(new Slot(container, 2, 79, 58));
        this.addSlot(new Slot(container, 3, 102, 51));
        this.addSlot(new Slot(container, 4, 17, 17));
        int invStartX = 8;
        int invStartY = 84;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        invStartX + col * 18, invStartY + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col,
                    invStartX + col * 18, invStartY + 58));
        }
    }

    public BrewingMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, new SimpleContainer(5));
    }

    @Override
    public int getContainerSize() {
        return 5;
    }
}

