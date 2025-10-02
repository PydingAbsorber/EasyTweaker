package com.pyding.easy_tweaker.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.level.CraftTweakerSavedData;
import com.pyding.easy_tweaker.EasyTweaker;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecipeManager extends Item {
    public RecipeManager(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41434_) {
        if (level.isClientSide())
            return super.use(level, player, p_41434_);
        List<String> list = new ArrayList<>();
        list.add("item:minecraft:dirt");
        EasyUtil.writeRecipe(EasyUtil.addShapeless("item:minecraft:apple","apple",2,list),player);
        return super.use(level, player, p_41434_);
    }
}
