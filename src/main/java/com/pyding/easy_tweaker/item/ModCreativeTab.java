package com.pyding.easy_tweaker.item;

import com.pyding.easy_tweaker.EasyTweaker;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EasyTweaker.MODID);

    public static void register(IEventBus eventBus){
        CREATIVE_MOD_TABS.register(eventBus);
    }

    public static RegistryObject<CreativeModeTab> VP_TAB = CREATIVE_MOD_TABS.register("vp_tab",()->
            CreativeModeTab.builder()
                    .icon(() ->  new ItemStack(ModItems.RecipeManager.get()))
                    .title(Component.translatable("itemGroup.vptab"))
                    .withBackgroundLocation(new ResourceLocation("easy_tweaker", "textures/gui/background.png"))
                    .build());
}
