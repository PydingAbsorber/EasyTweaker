package com.pyding.easy_tweaker.client;


import com.pyding.easy_tweaker.menu.SmithingMenu;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class SmithingScreen extends TweakerScreen<SmithingMenu> {

    public SmithingScreen(SmithingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public String getBGResourceLocation() {
        return "textures/gui/container/smithing.png";
    }

    @Override
    public String getRecipe() {
        List<String> items = this.menu.listAllItems();
        String main = items.get(3);
        items.remove(3);
        String nbt = "";
        if(switchTagOn)
            nbt = ".withTag(" + menu.getMainNbt() +")";
        String name = main;
        if(!recipeName.getValue().isEmpty())
            name = recipeName.getValue();
        int quant = 1;
        if(!quantity.getValue().isEmpty())
            quant = Integer.parseInt(quantity.getValue());
        if(switchOn)
            return EasyUtil.addSmithy(main,name,items.get(1),items.get(0),items.get(2),nbt,quant);
        return EasyUtil.removeSmithy(main);
    }
}

