package com.pyding.easy_tweaker.menu;

import com.pyding.easy_tweaker.EasyTweaker;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, EasyTweaker.MODID);

    public static final RegistryObject<MenuType<TweakerMenu>> TWEAKER_MENU =
            MENUS.register("tweaker_menu",
                    () -> IForgeMenuType.create(TweakerMenu::new));
}
