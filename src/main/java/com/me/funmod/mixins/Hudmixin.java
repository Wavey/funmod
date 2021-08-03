package com.me.funmod.mixins;

import com.me.funmod.FunMod;
import com.me.funmod.general.PlayerEntityNetherInterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.Identifier;

@Mixin(InGameHud.class)
public abstract class Hudmixin extends DrawableHelper  {

    @Shadow
    abstract PlayerEntity getCameraPlayer();
    @Shadow
    MinecraftClient client;

    @Shadow int scaledWidth;
    @Shadow int scaledHeight;


    private static final Identifier GUI_ICONS = new Identifier("textures/gui/icons.png");
    private static final Identifier BLUE_SHIELD = new Identifier(FunMod.ModID + ":textures/gui/blueshield_hud.png");



    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V", cancellable = true)
    private void renderer( MatrixStack matrices, float f,CallbackInfo info) {
        this.client.getTextureManager().bindTexture(BLUE_SHIELD);
        PlayerEntity playerEntity = this.getCameraPlayer();
        PlayerEntityNetherInterface netherInterface = (PlayerEntityNetherInterface) playerEntity;
        //System.out.println(netherInterface.getNetherTimer());
        this.drawTexture(matrices, this.scaledWidth / 2 + 91 , this.scaledHeight - 39, 0, 0, 16, 16, 16, 16);

    }

}
