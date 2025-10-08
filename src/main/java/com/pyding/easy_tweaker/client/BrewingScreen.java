package com.pyding.easy_tweaker.client;

import com.pyding.easy_tweaker.menu.BrewingMenu;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;

import java.util.List;

public class BrewingScreen extends TweakerScreen<BrewingMenu> {

    public BrewingScreen(BrewingMenu menu, net.minecraft.world.entity.player.Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    public String getBGResourceLocation() {
        return "textures/gui/container/brewing_stand.png";
    }

    @Override
    public String getRecipe() {
        List<String> items = menu.listAllItems();
        String main = items.get(2);
        items.remove(2);
        String nbt = "";
        ItemStack mainStack = this.menu.getMainStack();
        boolean potion = mainStack.getItem() instanceof PotionItem;
        if(switchTagOn && !potion)
            nbt = ".withTag(" + menu.getMainNbt() +")";
        if(switchOn)
            return EasyUtil.addBrew(main,items.get(0),items.get(1),nbt);
        return EasyUtil.removeBrew(main,items.get(0),items.get(1));
    }
}

