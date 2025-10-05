package com.pyding.easy_tweaker.mixin;

import com.mojang.blaze3d.platform.Window;
import com.pyding.easy_tweaker.client.TweakerScreen;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Window.class)
public abstract class GuiScaleMixin {

    @Inject(method = "getGuiScaledHeight", at = @At("HEAD"), cancellable = true)
    public void guiHeightMixin(CallbackInfoReturnable<Integer> cir) {
        if (Minecraft.getInstance().screen instanceof TweakerScreen) {
            Window window = (Window) (Object) (this);
            double guiScale = window.calculateScale(2, Minecraft.getInstance().isEnforceUnicode());
            cir.setReturnValue((int) Math.floor(window.getHeight() / guiScale)+1);
        }
    }

    @Inject(method = "getGuiScaledWidth", at = @At("HEAD"), cancellable = true)
    public void guiWidthMixin(CallbackInfoReturnable<Integer> cir) {
        if (Minecraft.getInstance().screen instanceof TweakerScreen) {
            Window window = (Window) (Object) (this);
            double guiScale = window.calculateScale(2, Minecraft.getInstance().isEnforceUnicode());
            cir.setReturnValue((int) Math.floor(window.getWidth() / guiScale)+1);
        }
    }

    @Inject(method = "getGuiScale", at = @At("HEAD"), cancellable = true)
    public void guiScaleMixin(CallbackInfoReturnable<Double> cir) {
        if (Minecraft.getInstance().screen instanceof TweakerScreen) {
            cir.setReturnValue((double) ((Window)(Object)(this)).calculateScale(2, Minecraft.getInstance().isEnforceUnicode()));
        }
    }
}
