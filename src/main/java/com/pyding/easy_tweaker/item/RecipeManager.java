package com.pyding.easy_tweaker.item;

import com.pyding.easy_tweaker.menu.*;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RecipeManager extends Item {
    public RecipeManager(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level p_41405_, Entity entity, int p_41407_, boolean p_41408_) {
        if(entity instanceof Player player && player instanceof ServerPlayer serverPlayer && !serverPlayer.hasPermissions(2)) {
            stack.shrink(1);
            serverPlayer.sendSystemMessage(Component.literal("You don't have enough rights to have this item."));
        }
        super.inventoryTick(stack, p_41405_, entity, p_41407_, p_41408_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41434_) {
        if(!player.hasPermissions(2)) {
            player.sendSystemMessage(Component.literal("You don't have enough rights to use this gui."));
            return super.use(level, player, p_41434_);
        }
        if(level.isClientSide())
            Minecraft.getInstance().resizeDisplay();
        switch (player.getMainHandItem().getOrCreateTag().getInt("TweakGui")){
            case 3:
                RecipeManager.openFurnaceGUI(player);
                break;
            case 4:
                RecipeManager.openSmithyGUI(player);
                break;
            case 5:
                RecipeManager.openBrewingGUI(player);
                break;
            case 6:
                RecipeManager.openBotaniaGUI(player);
                break;
            default: RecipeManager.openWorkbenchGUI(player);
        }
        return super.use(level, player, p_41434_);
    }

    public static void openWorkbenchGUI(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, playerEntity) ->
                            new WorkbenchMenu(containerId, playerInventory),
                    Component.literal("Easy Tweaker Menu")
            ));
        }
    }

    public static void openSmithyGUI(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, playerEntity) ->
                            new SmithingMenu(containerId, playerInventory),
                    Component.literal("Easy Tweaker Menu")
            ));
        }
    }

    public static void openFurnaceGUI(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, playerEntity) ->
                            new FurnaceMenu(containerId, playerInventory),
                    Component.literal("Easy Tweaker Menu")
            ));
        }
    }

    public static void openBrewingGUI(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, playerEntity) ->
                            new BrewingMenu(containerId, playerInventory),
                    Component.literal("Easy Tweaker Menu")
            ));
        }
    }

    public static void openBotaniaGUI(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, playerEntity) ->
                            new WorkbenchMenu(containerId, playerInventory),
                    Component.literal("Easy Tweaker Menu")
            ));
        }
    }
}
