package com.pyding.easy_tweaker.client;

import com.pyding.easy_tweaker.menu.FurnaceMenu;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.network.chat.Component;

import java.util.List;

public class FurnaceScreen extends TweakerScreen<FurnaceMenu> {

    public FurnaceScreen(FurnaceMenu menu, net.minecraft.world.entity.player.Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    public String getBGResourceLocation() {
        return "textures/gui/container/furnace.png";
    }

    @Override
    public String getRecipe() {
        List<String> items = this.menu.listAllItems();
        String main = items.get(1);
        items.remove(1);
        if(switchOn)
            return EasyUtil.addFurnace(main,main,1,items.get(0),1,1);
        return EasyUtil.removeFurnace(items.get(0),main);
    }
}

