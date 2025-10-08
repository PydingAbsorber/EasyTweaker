package com.pyding.easy_tweaker.client;

import com.pyding.easy_tweaker.menu.FurnaceMenu;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;

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
        String nbt = "";
        ItemStack mainStack = this.menu.getMainStack();
        boolean potion = mainStack.getItem() instanceof PotionItem;
        if(switchTagOn && !potion)
            nbt = ".withTag(" + menu.getMainNbt() +")";
        String name = main;
        if(potion)
            name = mainStack.getDescriptionId().replaceAll("\\.","_");
        if(!recipeName.getValue().isEmpty())
            name = recipeName.getValue();
        int quant = 1;
        if(!quantity.getValue().isEmpty())
            quant = Integer.parseInt(quantity.getValue());
        int time = 1;
        if(blastOn)
            return EasyUtil.addBlastFurnace(main,name,quant,items.get(0),0,time*20,nbt);
        if(switchOn)
            return EasyUtil.addFurnace(main,name,quant,items.get(0),0,time*20,nbt);
        return EasyUtil.removeFurnace(main);
    }
}

