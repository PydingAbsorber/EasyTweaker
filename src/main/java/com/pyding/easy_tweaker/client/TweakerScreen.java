package com.pyding.easy_tweaker.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pyding.easy_tweaker.EasyTweaker;
import com.pyding.easy_tweaker.menu.TweakerMenu;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class TweakerScreen extends AbstractContainerScreen<TweakerMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
    private static final ResourceLocation BUTTON = new ResourceLocation(EasyTweaker.MODID, "textures/gui/button.png");
    private static final ResourceLocation SWITCH_ACTIVE = new ResourceLocation(EasyTweaker.MODID, "textures/gui/switch_active.png");
    private static final ResourceLocation SWITCH_UNACTIVE = new ResourceLocation(EasyTweaker.MODID, "textures/gui/switch_unactive.png");

    public static String recipe = "";
    public static boolean switchOn = true;

    public TweakerScreen(TweakerMenu menu, net.minecraft.world.entity.player.Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(BACKGROUND, x, y, 0, 0, this.imageWidth, this.imageHeight);
        List<String> items = this.menu.listAllItems();
        String main = items.get(9);
        items.remove(9);
        int textWidth = this.width * 4 / 5;
        int textStartX = (this.width - textWidth) / 2;
        int textY = this.height - this.height / 5;
        String longText = "";
        if(switchOn)
            longText = EasyUtil.addShapeless(main, main, 1, items);
        else longText = EasyUtil.removeRecipeCraftingTable(main);
        int lineHeight = this.font.lineHeight;
        List<FormattedCharSequence> lines = this.font.split(Component.literal(longText), textWidth);
        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(this.font, lines.get(i), textStartX, textY + (i * lineHeight), 0xFFFFFF, false);
        }
        recipe = longText;
        switchActive.visible = switchOn;
        switchUnactive.visible = !switchOn;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public static Button switchActive;
    public static Button switchUnactive;

    @Override
    protected void init() {
        super.init();
        int buttonX = 270/2;
        int buttonY = 110/2;
        int x = this.width/2 - buttonX/2;
        int y = this.height - this.height/3 - buttonY/2;
        Button copy = new ImageButton(
                x-buttonX, y,
                buttonX, buttonY,
                0, 0, 0,
                BUTTON,
                buttonX, buttonY,
                button -> copyText(recipe),
                Component.translatable("et.button.copy")
        );
        this.addRenderableWidget(copy);
        Button addRecipe = new ImageButton(
                x, y,
                buttonX, buttonY,
                0, 0, 0,
                BUTTON,
                buttonX, buttonY,
                button -> copyText(recipe),
                Component.translatable("et.button.add")
        );
        this.addRenderableWidget(addRecipe);
        Button reload = new ImageButton(
                x+buttonX, y,
                buttonX, buttonY,
                0, 0, 0,
                BUTTON,
                buttonX, buttonY,
                button -> copyText(recipe),
                Component.translatable("et.button.reload")
        );
        this.addRenderableWidget(reload);
        int buttonSize = 32*2;
        x = this.width/2 - buttonSize/2;
        y = this.height - this.height/3 + buttonSize/3;
        switchActive = new ImageButton(
                x, y,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_ACTIVE,
                buttonSize, buttonSize,
                button -> switchOn = false
        );
        switchActive.setTooltip(Tooltip.create(Component.translatable("et.switch.on")));
        this.addRenderableWidget(switchActive);
        switchUnactive = new ImageButton(
                x, y,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_UNACTIVE,
                buttonSize, buttonSize,
                button -> switchOn = true
        );
        switchUnactive.setTooltip(Tooltip.create(Component.translatable("et.switch.off")));
        this.addRenderableWidget(switchUnactive);
    }

    private void copyText(String text) {
        Minecraft.getInstance().keyboardHandler.setClipboard(text);
    }
}
