package com.pyding.easy_tweaker;

import com.pyding.easy_tweaker.item.ModCreativeTab;
import com.pyding.easy_tweaker.item.ModItems;
import com.pyding.easy_tweaker.menu.ModMenus;
import com.pyding.easy_tweaker.network.PacketHandler;
import com.pyding.easy_tweaker.util.ConfigHandler;
import com.pyding.easy_tweaker.event.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EasyTweaker.MODID)
public class EasyTweaker
{
    public static final String MODID = "easy_tweaker";
    public static EventHandler eventHandler;

    public EasyTweaker()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        eventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.COMMON_SPEC);
        ModItems.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        PacketHandler.register();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ModCreativeTab.VP_TAB.get()) {
            event.accept(ModItems.RecipeManager);
        }
    }
}
