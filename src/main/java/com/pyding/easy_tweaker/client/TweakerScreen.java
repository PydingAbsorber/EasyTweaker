package com.pyding.easy_tweaker.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pyding.easy_tweaker.EasyTweaker;
import com.pyding.easy_tweaker.item.RecipeManager;
import com.pyding.easy_tweaker.menu.TweakerMenu;
import com.pyding.easy_tweaker.network.PacketHandler;
import com.pyding.easy_tweaker.network.packets.GuiPacket;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public abstract class TweakerScreen<T extends TweakerMenu> extends AbstractContainerScreen<T> {
    private ResourceLocation BACKGROUND = null;
    private static final ResourceLocation BUTTON = new ResourceLocation(EasyTweaker.MODID, "textures/gui/button.png");
    private static final ResourceLocation SWITCH_ACTIVE = new ResourceLocation(EasyTweaker.MODID, "textures/gui/switch_active.png");
    private static final ResourceLocation SWITCH_UNACTIVE = new ResourceLocation(EasyTweaker.MODID, "textures/gui/switch_unactive.png");

    public String getRecipe(){
        return "";
    }

    public T menu = null;

    public ResourceLocation getBackground(){
        if(BACKGROUND == null)
            BACKGROUND = new ResourceLocation(getBGDirectory(), getBGResourceLocation());
        return BACKGROUND;
    }

    public String getBGDirectory(){
        return "minecraft";
    }

    public String getBGResourceLocation(){
        return "";
    }

    public static boolean switchOn = true;
    public static boolean switchTagOn = false;
    public static boolean shapeOn = false;
    public static boolean blastOn = false;

    public TweakerScreen(T menu, net.minecraft.world.entity.player.Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.menu = menu;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, getBackground());
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(getBackground(), x, y, 0, 0, this.imageWidth, this.imageHeight);
        int textWidth = (this.width * 4) / 5;
        int textStartX = (this.width - textWidth) / 2;
        int textY = switchActive.getY() + switchActive.getY() / 6;
        boolean workbench = this instanceof WorkbenchScreen;
        boolean furnaceGui = this instanceof FurnaceScreen;
        if(workbench)
            textY = shaped.getY() + shaped.getY() / 6;
        if(furnaceGui)
            textY = blastFurnace.getY() + blastFurnace.getY() / 6;
        String longText = getRecipe();
        int lineHeight = this.font.lineHeight;
        List<FormattedCharSequence> lines = this.font.split(Component.literal(longText), textWidth);
        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(this.font, lines.get(i), textStartX, textY + (i * lineHeight), 0xFFFFFF, false);
        }
        switchActive.visible = switchOn;
        switchUnactive.visible = !switchOn;
        tagSwitchActive.visible = switchTagOn;
        tagSwitchUnactive.visible = !switchTagOn;
        shaped.visible = !shapeOn & workbench;
        shapeless.visible = shapeOn & workbench;
        blastFurnace.visible = !blastOn & furnaceGui;
        furnace.visible = blastOn & furnaceGui;
        int buttonX = 270/5-20;
        int buttonY = 110/5;
        guiGraphics.drawString(this.font, Component.translatable("et.button.copy"), buttonX+copy.getX(), buttonY+copy.getY(), 0xFFFFFF, true);
        guiGraphics.drawString(this.font, Component.translatable("et.button.add"), buttonX+addRecipe.getX(), buttonY+addRecipe.getY(), 0xFFFFFF, true);
        guiGraphics.drawString(this.font, Component.translatable("et.button.reload"), buttonX+reload.getX(), buttonY+reload.getY(), 0xFFFFFF, true);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private static final ResourceLocation ICON_CRAFTING = new ResourceLocation(EasyTweaker.MODID, "textures/gui/workbench.png");
    private static final ResourceLocation ICON_FURNACE = new ResourceLocation(EasyTweaker.MODID, "textures/gui/furnace.png");
    private static final ResourceLocation ICON_SMITHING = new ResourceLocation(EasyTweaker.MODID, "textures/gui/smithing.png");
    private static final ResourceLocation ICON_BREWING = new ResourceLocation(EasyTweaker.MODID, "textures/gui/brewing.png");

    protected ImageButton craftingIcon;
    protected ImageButton furnaceIcon;
    protected ImageButton smithingIcon;
    protected ImageButton brewingIcon;

    public static Button switchActive;
    public static Button switchUnactive;
    public static Button tagSwitchActive;
    public static Button tagSwitchUnactive;
    public static Button copy;
    public static Button addRecipe;
    public static Button reload;
    public static EditBox quantity;
    public static EditBox recipeName;
    public static Button shapeless;
    public static Button shaped;
    public static Button blastFurnace;
    public static Button furnace;

    @Override
    protected void init() {
        super.init();
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null)
            return;
        if(!player.hasPermissions(2)) {
            player.sendSystemMessage(Component.literal("You don't have enough rights to use this gui."));
            this.onClose();
        }
        int buttonX = 270/2;
        int buttonY = 110/2;
        int x = this.width/2 - buttonX/2;
        int y = this.getGuiTop() + this.getYSize();
        copy = new ImageButton(
                x-buttonX, y,
                buttonX, buttonY,
                0, 0, 0,
                BUTTON,
                buttonX, buttonY,
                button -> copyText(getRecipe())
        );
        this.addRenderableWidget(copy);
        addRecipe = new ImageButton(
                x, y,
                buttonX, buttonY,
                0, 0, 0,
                BUTTON,
                buttonX, buttonY,
                button -> PacketHandler.sendToServer(new GuiPacket(player.getUUID(),0,getRecipe()))
        );
        this.addRenderableWidget(addRecipe);
        reload = new ImageButton(
                x+buttonX, y,
                buttonX, buttonY,
                0, 0, 0,
                BUTTON,
                buttonX, buttonY,
                button -> PacketHandler.sendToServer(new GuiPacket(player.getUUID(),1))
        );
        this.addRenderableWidget(reload);
        int buttonSize = 32*2;
        x = this.width/2 - buttonSize/2;
        y += buttonSize;
        switchActive = new ImageButton(
                x+buttonSize/2, y,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_ACTIVE,
                buttonSize, buttonSize,
                button -> switchOn = false
        );
        switchActive.setTooltip(Tooltip.create(Component.translatable("et.switch.on")));
        this.addRenderableWidget(switchActive);
        switchUnactive = new ImageButton(
                x+buttonSize/2, y,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_UNACTIVE,
                buttonSize, buttonSize,
                button -> switchOn = true
        );
        switchUnactive.setTooltip(Tooltip.create(Component.translatable("et.switch.off")));
        this.addRenderableWidget(switchUnactive);
        tagSwitchActive = new ImageButton(
                x-buttonSize/2, y,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_ACTIVE,
                buttonSize, buttonSize,
                button -> switchTagOn = false
        );
        tagSwitchActive.setTooltip(Tooltip.create(Component.translatable("et.switch_tag.on")));
        this.addRenderableWidget(tagSwitchActive);
        tagSwitchUnactive = new ImageButton(
                x-buttonSize/2, y,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_UNACTIVE,
                buttonSize, buttonSize,
                button -> switchTagOn = true
        );
        tagSwitchUnactive.setTooltip(Tooltip.create(Component.translatable("et.switch_tag.off")));
        this.addRenderableWidget(tagSwitchUnactive);

        shapeless = new ImageButton(
                x, y + buttonSize - buttonSize/3,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_ACTIVE,
                buttonSize, buttonSize,
                button -> shapeOn = false
        );
        shapeless.setTooltip(Tooltip.create(Component.translatable("et.switch_shape.on")));
        this.addRenderableWidget(shapeless);
        shaped = new ImageButton(
                x, y + buttonSize - buttonSize/3,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_UNACTIVE,
                buttonSize, buttonSize,
                button -> shapeOn = true
        );
        shaped.setTooltip(Tooltip.create(Component.translatable("et.switch_shape.off")));
        this.addRenderableWidget(shaped);

        blastFurnace = new ImageButton(
                x, y + buttonSize - buttonSize/3,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_ACTIVE,
                buttonSize, buttonSize,
                button -> blastOn = true
        );
        blastFurnace.setTooltip(Tooltip.create(Component.translatable("et.switch_blast.on")));
        this.addRenderableWidget(blastFurnace);
        furnace = new ImageButton(
                x, y + buttonSize - buttonSize/3,
                buttonSize, buttonSize,
                0, 0, 0,
                SWITCH_UNACTIVE,
                buttonSize, buttonSize,
                button -> blastOn = false
        );
        furnace.setTooltip(Tooltip.create(Component.translatable("et.switch_blast.off")));
        this.addRenderableWidget(furnace);

        int boxSizeX = 120;
        int boxSizeY = 26;
        x = this.width/2 - boxSizeX/2;

        quantity = new EditBox(this.font, x+boxSizeX+buttonSize/2-16, y+boxSizeY/2+6, boxSizeX, boxSizeY, Component.literal("Quantity"));
        quantity.setMaxLength(100);
        quantity.setValue("");
        quantity.setBordered(true);
        quantity.setTooltip(Tooltip.create(Component.literal("Quantity")));
        this.addRenderableWidget(quantity);
        recipeName = new EditBox(this.font, x-boxSizeX-buttonSize/2+16, y+boxSizeY/2+6, boxSizeX, boxSizeY, Component.literal("Recipe Name"));
        recipeName.setMaxLength(100);
        recipeName.setValue("");
        recipeName.setBordered(true);
        recipeName.setTooltip(Tooltip.create(Component.literal("Recipe Name")));
        this.addRenderableWidget(recipeName);

        int iconSize = 64;
        int spacing = 16;
        int totalWidth = iconSize * 4 + spacing * 3;
        int startX = (this.width - totalWidth) / 2;
        y = this.topPos - 40 - iconSize;

        craftingIcon = new ImageButton(startX, y, iconSize, iconSize, 0, 0, 0, ICON_CRAFTING, iconSize, iconSize,
                btn -> PacketHandler.sendToServer(new GuiPacket(player.getUUID(),2)));
        this.addRenderableWidget(craftingIcon);

        furnaceIcon = new ImageButton(startX + (iconSize + spacing), y, iconSize, iconSize, 0, 0, 0, ICON_FURNACE, iconSize, iconSize,
                btn -> PacketHandler.sendToServer(new GuiPacket(player.getUUID(),3)));
        this.addRenderableWidget(furnaceIcon);

        smithingIcon = new ImageButton(startX + 2 * (iconSize + spacing), y, iconSize, iconSize, 0, 0, 0, ICON_SMITHING, iconSize, iconSize,
                btn -> PacketHandler.sendToServer(new GuiPacket(player.getUUID(),4)));
        this.addRenderableWidget(smithingIcon);

        brewingIcon = new ImageButton(startX + 3 * (iconSize + spacing), y, iconSize, iconSize, 0, 0, 0, ICON_BREWING, iconSize, iconSize,
                btn -> PacketHandler.sendToServer(new GuiPacket(player.getUUID(),5)));
        this.addRenderableWidget(brewingIcon);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (recipeName.isFocused()) {
            if (keyCode == GLFW.GLFW_KEY_E) {
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void copyText(String text) {
        Minecraft.getInstance().keyboardHandler.setClipboard(text);
    }
}
