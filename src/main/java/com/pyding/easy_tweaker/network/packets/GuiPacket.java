package com.pyding.easy_tweaker.network.packets;

import com.pyding.easy_tweaker.item.RecipeManager;
import com.pyding.easy_tweaker.util.EasyUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class GuiPacket {
    private UUID uuid;
    private int number;
    private String additional;

    public GuiPacket(UUID uuid, int number) {
        this.uuid = uuid;
        this.number = number;
        this.additional = "";
    }

    public GuiPacket(UUID uuid, int number, String additional) {
        this.uuid = uuid;
        this.number = number;
        this.additional = additional;
    }

    public static void encode(GuiPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.uuid);
        buf.writeInt(msg.number);
        buf.writeUtf(msg.additional);
    }

    public static GuiPacket decode(FriendlyByteBuf buf) {
        return new GuiPacket(buf.readUUID(),buf.readInt(),buf.readUtf());
    }

    public static boolean handle(GuiPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(msg.uuid.equals(player.getUUID()) && player.hasPermissions(2) && player.getMainHandItem().getItem() instanceof RecipeManager) {
                switch (msg.number){
                    case 0:
                        EasyUtil.writeRecipe(msg.additional,player);
                        break;
                    case 1:
                        player.getServer().getCommands().performPrefixedCommand(player.createCommandSourceStack(), "/reload");
                        break;
                    case 2:
                        RecipeManager.openWorkbenchGUI(player);
                        break;
                    case 3:
                        RecipeManager.openFurnaceGUI(player);
                        break;
                    case 4:
                        RecipeManager.openSmithyGUI(player);
                        break;
                    case 5:
                        RecipeManager.openBrewingGUI(player);
                        break;
                    case 6:
                        RecipeManager.openBotaniaGUI(player);
                        break;
                }
            }
        });
        return true;
    }
}
