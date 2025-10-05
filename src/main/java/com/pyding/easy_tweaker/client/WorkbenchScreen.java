package com.pyding.easy_tweaker.client;

import com.pyding.easy_tweaker.menu.WorkbenchMenu;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.network.chat.Component;

import java.util.List;

public class WorkbenchScreen extends TweakerScreen<WorkbenchMenu> {

    public WorkbenchScreen(WorkbenchMenu menu, net.minecraft.world.entity.player.Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    public String getBGResourceLocation() {
        return "textures/gui/container/crafting_table.png";
    }

    @Override
    public String getRecipe(){
        List<String> items = this.menu.listAllItems();
        String main = items.get(9);
        items.remove(9);
        if(switchOn)
            return EasyUtil.addShapeless(main, main, 1, items);
        else return EasyUtil.removeRecipeCraftingTable(main);
    }
}
