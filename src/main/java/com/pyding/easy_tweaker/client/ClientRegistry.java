package com.pyding.easy_tweaker.client;

import com.pyding.easy_tweaker.EasyTweaker;
import com.pyding.easy_tweaker.menu.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EasyTweaker.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistry {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.WORKBENCH_MENU.get(), WorkbenchScreen::new);
            MenuScreens.register(ModMenus.FURNACE_MENU.get(), FurnaceScreen::new);
            MenuScreens.register(ModMenus.BREWING_MENU.get(), BrewingScreen::new);
            MenuScreens.register(ModMenus.SMITHING_MENU.get(), SmithingScreen::new);
        });
    }
}
