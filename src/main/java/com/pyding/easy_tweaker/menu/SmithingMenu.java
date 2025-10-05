package com.pyding.easy_tweaker.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class SmithingMenu extends TweakerMenu {

    public SmithingMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(4));
    }

    public SmithingMenu(int containerId, Inventory playerInventory, SimpleContainer container) {
        super(ModMenus.SMITHING_MENU.get(), containerId);

        this.addSlot(new Slot(container, 0, 8, 48));
        this.addSlot(new Slot(container, 1, 26, 48));
        this.addSlot(new Slot(container, 2, 44, 48));
        this.addSlot(new Slot(container, 3, 98, 48));

        int invStartX = 8;
        int invStartY = 84;
        for (int row = 0; row < 3; ++row)
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        invStartX + col * 18, invStartY + row * 18));

        for (int col = 0; col < 9; ++col)
            this.addSlot(new Slot(playerInventory, col,
                    invStartX + col * 18, invStartY + 58));
    }

    public SmithingMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, new SimpleContainer(4));
    }

    @Override
    public int getContainerSize() {
        return 4;
    }
}
