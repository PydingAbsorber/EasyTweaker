package com.pyding.easy_tweaker.util;

import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.pyding.easy_tweaker.EasyTweaker;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EasyUtil {

    public static String removeFurnace(String item, String smeltedItem) {
        return "furnace.remove(<" + smeltedItem + ">, <" + item + ">);";
    }

    public static String removeSmithy(String item) {
        return "smithing.remove(<tag:items:" + item.replaceFirst("^item:", "") + ">);";
    }

    public static String removeRecipeCraftingTable(String item) {
        return "craftingTable.removeByName(\"" + item + "\");";
    }

    public static String removeBrew(String result,String reagent, String input) {
        return "brewing.removeRecipe(<" + result + ">, <" + reagent + ">, <" + input + ">);";
    }

    public static String addShapeless(String result, String name, int count,
                                      List<String> list) {
        int size = list.size();
        if(size < 9){
            for(int i = 0; i < 9-size;i++){
                list.add("item:minecraft:air");
            }
        }
        return "craftingTable.addShapeless(\"" + name + "\", " +
                "<" + result + "> * " + count + ", " +
                "[" +
                "<" + list.get(0) + ">, " +
                "<" + list.get(1) + ">, " +
                "<" + list.get(2) + ">, " +
                "<" + list.get(3) + ">, " +
                "<" + list.get(4) + ">, " +
                "<" + list.get(5) + ">, " +
                "<" + list.get(6) + ">, " +
                "<" + list.get(7) + ">, " +
                "<" + list.get(8) + ">" +
                "]" +
                ");";
    }

    public static String addFurnace(String result, String name, int count,
                                      String rawItem, float xp, int time) {
        return "furnace.addRecipe(\"" + name + "\", " +
                "<" + result + "> * " + count + ", " +
                "<" + rawItem + ">, " +
                xp + ", " +
                time +
                ");";
    }

    public static String addSmithy(String result, String name, String base, String template, String material) {
        return "smithing.addTransformRecipe(\"" + name + "\", " +
                "<" + result + ">, " +
                "<" + template + ">, " +
                "<" + base + ">, " +
                "<" + material + ">, " +
                ");";
    }

    public static String addBrew(String result,String reagent, String input) {
        return "brewing.addRecipe(<" + result + ">, <" + reagent + ">, <" + input + ">);";
    }

    public static String addPetal(String result, String name, int count,
                                      List<String> list) {
        int size = list.size();
        if(size < 9){
            for(int i = 0; i < 9-size;i++){
                list.add("item:minecraft:air");
            }
        }
        return "<recipetype:botania:petal_apothecary>.add(\"" + name + "\", " +
                "<" + result + "> * " + count + ", " +
                "[" +
                "<" + list.get(0) + ">, " +
                "<" + list.get(1) + ">, " +
                "<" + list.get(2) + ">, " +
                "<" + list.get(3) + ">, " +
                "<" + list.get(4) + ">, " +
                "<" + list.get(5) + ">, " +
                "<" + list.get(6) + ">, " +
                "<" + list.get(7) + ">, " +
                "<" + list.get(8) + ">" +
                "]" +
                ");";
    }

    public static void writeRecipe(String recipe, Player player){
        String jarPath = EasyTweaker.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File jarFile = new File(jarPath);
        File modsDir = jarFile.getParentFile();
        if (modsDir == null || !modsDir.getName().equals("mods")) {
            player.sendSystemMessage(Component.literal("Can't locate mods folder."));
            return;
        }

        File mcRoot = modsDir.getParentFile();
        if (mcRoot == null) {
            player.sendSystemMessage(Component.literal("Can't locate minecraft folder."));
            return;
        }

        File scriptsDir = new File(mcRoot, "scripts");
        if (!scriptsDir.exists()) {
            scriptsDir.mkdirs();
        }

        File[] zsFiles = scriptsDir.listFiles((dir, nameFile) -> nameFile.endsWith(".zs"));
        File targetFile;
        if (zsFiles != null && zsFiles.length > 0) {
            targetFile = zsFiles[0];
        } else {
            targetFile = new File(scriptsDir, "auto_generated.zs");
        }

        try (FileWriter writer = new FileWriter(targetFile, true)) {
            writer.write(recipe + System.lineSeparator());
            writer.flush();
            player.sendSystemMessage(Component.literal("Recipe added."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
