package com.pyding.easy_tweaker.menu;

import com.pyding.easy_tweaker.item.RecipeManager;
import com.pyding.easy_tweaker.mixin.SmitingMixing;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TweakerMenu extends AbstractContainerMenu {

    public TweakerMenu(@Nullable MenuType<?> type, int id) {
        super(type,id);
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    public int getContainerSize(){
        return 1;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index >= 0 && index < getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(itemstack1, 0, getContainerSize(), false)) {
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
            for (int i = 0; i < getContainerSize(); i++) {
                Slot slot = this.slots.get(i);
                if (slot != null && slot.hasItem()) {
                    ItemStack stackInSlot = slot.getItem();
                    if (!playerInventory.add(stackInSlot.copy())) {
                        player.drop(stackInSlot, false);
                    }
                    slot.set(ItemStack.EMPTY);
                }
            }
            if(player.getMainHandItem().getItem() instanceof RecipeManager){
                int id = 1;
                if(this instanceof FurnaceMenu)
                    id = 3;
                else if(this instanceof SmithingMenu)
                    id = 4;
                else if(this instanceof BrewingMenu)
                    id = 5;
                player.getMainHandItem().getOrCreateTag().putInt("TweakGui",id);
            }
        }
    }

    public List<String> listAllItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < getContainerSize(); i++) {
            Slot slot = this.slots.get(i);
            if (slot.hasItem()) {
                if (slot.getItem().getItem() instanceof SmithingTemplateItem templateItem && ((SmitingMixing) templateItem).upgradeDescription().getContents() instanceof TranslatableContents translatableContents)
                    items.add(translatableContents.getKey());
                else items.add(slot.getItem().getDescriptionId().replaceAll("\\.",":"));
            } else items.add("item:minecraft:air");
        }
        return items;
    }
}
