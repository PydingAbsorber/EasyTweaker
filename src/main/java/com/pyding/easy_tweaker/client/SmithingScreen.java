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
        if(switchOn)
            return EasyUtil.addSmithy(main,main,items.get(1),items.get(0),items.get(2));
        return EasyUtil.removeSmithy(main);
    }
}

