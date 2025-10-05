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

    public static final RegistryObject<MenuType<WorkbenchMenu>> WORKBENCH_MENU =
            MENUS.register("workbench_menu",
                    () -> IForgeMenuType.create(WorkbenchMenu::new));

    public static final RegistryObject<MenuType<FurnaceMenu>> FURNACE_MENU =
            MENUS.register("furnace_menu",
                    () -> IForgeMenuType.create(FurnaceMenu::new));

    public static final RegistryObject<MenuType<BrewingMenu>> BREWING_MENU =
            MENUS.register("brewing_menu",
                    () -> IForgeMenuType.create(BrewingMenu::new));

    public static final RegistryObject<MenuType<SmithingMenu>> SMITHING_MENU =
            MENUS.register("smithing_menu",
                    () -> IForgeMenuType.create(SmithingMenu::new));
}
