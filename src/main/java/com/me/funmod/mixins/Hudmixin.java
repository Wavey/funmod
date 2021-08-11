package com.me.funmod.mixins;

import com.me.funmod.FunMod;
import com.me.funmod.general.PlayerEntityNetherInterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
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
    private static final Identifier BLUE_SHIELD = new Identifier(FunMod.ModID + ":textures/gui/shield_9_hud.png");



    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V", cancellable = true)
    private void renderer( MatrixStack matrices, float f,CallbackInfo info) {
        this.client.getTextureManager().bindTexture(BLUE_SHIELD);
        PlayerEntity playerEntity = this.getCameraPlayer();
         int scale = this.scaledWidth/2+91-9;
        int offset =0;
        PlayerEntityNetherInterface netherInterface = (PlayerEntityNetherInterface) playerEntity;
        System.out.println(netherInterface.getNetherTimer());
        //int loop = (netherInterface.getNetherTimer() * 10 +1)/1000 ;
        int loop = (int)Math.ceil(((float)netherInterface.getNetherTimer()) / 100.0);
        if (playerEntity.world.getRegistryKey() == World.NETHER)

            while (loop > 0)  {

                this.drawTexture(matrices, scale - offset, this.scaledHeight - 50, 0, 0, 9, 9, 9, 9);
                offset = offset + 9;
                loop--;
            }
    }

}
