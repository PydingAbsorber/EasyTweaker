package com.pyding.easy_tweaker.util;

import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.pyding.easy_tweaker.EasyTweaker;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EasyUtil {

    public static String removeFurnace(String item, String smeltedItem) {
        return "furnace.remove(<" + smeltedItem + ">, <" + item + ">);";
    }

    public static String removeSmithy(String item) {
        return "smithing.remove(<tag:items:" + item.replaceFirst("^item:", "") + ">);";
    }

    public static String removeRecipeCraftingTable(String item) {
        return "craftingTable.removeByName(\"" + cutPrefix(item) + "\");";
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

        return "craftingTable.addShapeless(\"" + name.replaceAll(":","_") + "\", " +
                "<" + changePrefix(result) + "> * " + count + ", " +
                "[" +
                "<" + changePrefix(list.get(0)) + ">, " +
                "<" + changePrefix(list.get(1)) + ">, " +
                "<" + changePrefix(list.get(2)) + ">, " +
                "<" + changePrefix(list.get(3)) + ">, " +
                "<" + changePrefix(list.get(4)) + ">, " +
                "<" + changePrefix(list.get(5)) + ">, " +
                "<" + changePrefix(list.get(6)) + ">, " +
                "<" + changePrefix(list.get(7)) + ">, " +
                "<" + changePrefix(list.get(8)) + ">" +
                "]" +
                ");";
    }

    public static String addFurnace(String result, String name, int count,
                                      String rawItem, float xp, int time) {
        return "furnace.addRecipe(\"" + name.replaceAll(":","_") + "\", " +
                "<" + changePrefix(result)+ "> * " + count + ", " +
                "<" + changePrefix(rawItem) + ">, " +
                xp + ", " +
                time +
                ");";
    }

    public static String addSmithy(String result, String name, String base, String template, String material) {
        return "smithing.addTransformRecipe(\"" + name.replaceAll(":","_") + "\", " +
                "<" + changePrefix(result) + ">, " +
                "<" + changePrefix(template) + ">, " +
                "<" + changePrefix(base) + ">, " +
                "<" + changePrefix(material) + ">, " +
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
        return "<recipetype:botania:petal_apothecary>.add(\"" + name.replaceAll(":","_") + "\", " +
                "<" + result.replace("<block:", "<item:") + "> * " + count + ", " +
                "[" +
                "<" + changePrefix(list.get(0)) + ">, " +
                "<" + changePrefix(list.get(1)) + ">, " +
                "<" + changePrefix(list.get(2)) + ">, " +
                "<" + changePrefix(list.get(3)) + ">, " +
                "<" + changePrefix(list.get(4)) + ">, " +
                "<" + changePrefix(list.get(5)) + ">, " +
                "<" + changePrefix(list.get(6)) + ">, " +
                "<" + changePrefix(list.get(7)) + ">, " +
                "<" + changePrefix(list.get(8)) + ">" +
                "]" +
                ");";
    }

    public static void writeRecipe(String recipe, Player player){
        if(!player.hasPermissions(2))
            return;
        String jarPath = EasyTweaker.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File jarFile = new File(jarPath);
        File modsDir = jarFile.getParentFile();
        if (modsDir == null || !modsDir.getName().equals("mods")) {
            if(player != null)
                player.sendSystemMessage(Component.literal("Can't locate mods folder."));
            return;
        }

        File mcRoot = modsDir.getParentFile();
        if (mcRoot == null) {
            if(player != null)
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

    public static String cutPrefix(String remove) {
        String target = "item:";
        int index = remove.indexOf(target);
        if (index != -1) {
            return remove.substring(index + target.length());
        }
        target = "block:";
        index = remove.indexOf(target);
        if (index != -1) {
            return remove.substring(index + target.length());
        }
        return remove;
    }

    public static String changePrefix(String item) {
        String target = "block:";
        int index = item.indexOf(target);
        if (index != -1) {
            return item.replaceAll("block:","item:");
        }
        return item;
    }

    public List<String> listAllItems(List<Slot> slots) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Slot slot =slots.get(i);
            if (slot.hasItem()) {
                items.add(slot.getItem().getDescriptionId().replaceAll("\\.",":"));
            } else items.add("item:minecraft:air");
        }
        return items;
    }
}
