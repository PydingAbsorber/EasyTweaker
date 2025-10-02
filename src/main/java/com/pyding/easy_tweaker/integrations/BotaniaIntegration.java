package com.pyding.easy_tweaker.integrations;

import java.util.List;

public class BotaniaIntegration {

    public static String removePetalApothecary(String outputItem) {
        return "<recipetype:botania:petal_apothecary>.remove(<" + outputItem + ">);";
    }

    public static String addPetalApothecary(String name, String result, String... inputs) {
        StringBuilder in = new StringBuilder();
        for (int i = 0; i < inputs.length; i++) {
            in.append("<").append(inputs[i]).append(">");
            if (i < inputs.length - 1) in.append(", ");
        }
        return "<recipetype:botania:petal_apothecary>.addRecipe(\"" + name + "\", <" + result + ">, " + in + ");";
    }

    public static String removePureDaisy(String outputBlockState) {
        return "<recipetype:botania:pure_daisy>.remove(<" + outputBlockState + ">);";
    }

    public static String addPureDaisy(String name, String output, String input, int time) {
        return "<recipetype:botania:pure_daisy>.addRecipe(\"" + name + "\", <" + output + ">, <" + input + ">, " + time + ");";
    }

    public static String removeManaInfusion(String outputItem) {
        return "<recipetype:botania:mana_infusion>.remove(<" + outputItem + ">);";
    }

    public static String addManaInfusion(String name, String result, String input, int mana) {
        return "<recipetype:botania:mana_infusion>.addRecipe(\"" + name + "\", <" + result + ">, <" + input + ">, " + mana + ");";
    }

    public static String addManaInfusionCatalyst(String name, String result, String input, int mana, String catalyst) {
        return "<recipetype:botania:mana_infusion>.addRecipe(\"" + name + "\", <" + result + ">, <" + input + ">, " + mana + ", <" + catalyst + ">);";
    }

    public static String addManaInfusionGroup(String name, String result, String input, int mana, String catalyst, String group) {
        return "<recipetype:botania:mana_infusion>.addRecipe(\"" + name + "\", <" + result + ">, <" + input + ">, " + mana + ", <" + catalyst + ">, \"" + group + "\");";
    }

    public static String removeElvenTrade(String outputItem) {
        return "<recipetype:botania:elven_trade>.remove(<" + outputItem + ">);";
    }

    public static String addElvenTrade(String name, List<String> outputs, String... inputs) {
        StringBuilder out = new StringBuilder("[");
        for (int i = 0; i < outputs.size(); i++) {
            out.append("<").append(outputs.get(i)).append(">");
            if (i < outputs.size() - 1) out.append(", ");
        }
        out.append("]");

        StringBuilder in = new StringBuilder();
        for (int i = 0; i < inputs.length; i++) {
            in.append("<").append(inputs[i]).append(">");
            if (i < inputs.length - 1) in.append(", ");
        }

        return "<recipetype:botania:elven_trade>.addRecipe(\"" + name + "\", " + out + ", " + in + ");";
    }

    public static String removeRunicAltar(String outputItem) {
        return "<recipetype:botania:runic_altar>.remove(<" + outputItem + ">);";
    }

    public static String addRunicAltar(String name, String result, int mana, String... inputs) {
        StringBuilder in = new StringBuilder();
        for (int i = 0; i < inputs.length; i++) {
            in.append("<").append(inputs[i]).append(">");
            if (i < inputs.length - 1) in.append(", ");
        }
        return "<recipetype:botania:runic_altar>.addRecipe(\"" + name + "\", <" + result + ">, " + mana + ", " + in + ");";
    }

    public static String removeTerraPlate(String outputItem) {
        return "<recipetype:botania:terra_plate>.remove(<" + outputItem + ">);";
    }

    public static String addTerraPlate(String name, String result, int mana, String... inputs) {
        StringBuilder in = new StringBuilder();
        for (int i = 0; i < inputs.length; i++) {
            in.append("<").append(inputs[i]).append(">");
            if (i < inputs.length - 1) in.append(", ");
        }
        return "<recipetype:botania:terra_plate>.addRecipe(\"" + name + "\", <" + result + ">, " + mana + ", " + in + ");";
    }

    public static String addBrewRecipe(String name, String brew, String input) {
        return "<recipetype:botania:brew>.addRecipe(\"" + name + "\", <" + brew + ">, <" + input + ">);";
    }

    public static String removeBrewRecipe(String brew) {
        return "<recipetype:botania:brew>.remove(<" + brew + ">);";
    }

}
