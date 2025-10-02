package com.pyding.easy_tweaker.integrations;

import net.minecraftforge.fml.ModList;

public class IntegrationUtil {

    public static boolean isBotaniaLoaded(){
        return ModList.get().isLoaded("botania");
    }
}
