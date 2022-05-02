package com.me.funmod.mixins;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
abstract class HungerManagerMixin {
    @Shadow
    private int foodLevel;
    //@Shadow
    //private float foodSaturationLevel;
    @Shadow
    private float exhaustion;
    @Shadow
    private int prevFoodLevel;


    @Shadow
    public void addExhaustion(float exhaustion) { }


    /*
    @Inject(at = @At("HEAD"), method = "update(Lnet/minecraft/entity/player/PlayerEntity;)V", cancellable = true)
    private void onUpdate(PlayerEntity player, CallbackInfo info) {
        Difficulty difficulty = player.world.getDifficulty();
        this.prevFoodLevel = this.foodLevel;
        if (this.exhaustion > 4.0F) {
            this.exhaustion -= 4.0F;
            if (this.foodSaturationLevel > 0.0F) {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        boolean bl = player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        if (bl && this.foodSaturationLevel > 0.0F && player.canFoodHeal() && this.foodLevel >= 20 && (player.getHealth() < 9.5 || player.getHealth() > 10.5F)) {
            ++this.foodStarvationTimer;
            if (this.foodStarvationTimer >= 10) {
                float f = Math.min(this.foodSaturationLevel, 6.0F);
                System.out.println("Just healed player because of food saturation. health = " + player.getHealth());
                player.heal(f / 6.0F);
                this.addExhaustion(f);
                this.foodStarvationTimer = 0;
            }
        }
        // If the player health is below 1/2 then only let you
        // heal up to the 1/2 way point.
        else if (bl && this.foodLevel >= 18 && player.canFoodHeal() && (player.getHealth() < 9.5 || player.getHealth() > 10.5F)) {
            ++this.foodStarvationTimer;
            if (this.foodStarvationTimer >= 80) {
                System.out.println("Healing player.. health = " + player.getHealth());
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.foodStarvationTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            ++this.foodStarvationTimer;
            if (this.foodStarvationTimer >= 80) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.damage(DamageSource.STARVE, 1.0F);
                }

                this.foodStarvationTimer = 0;
            }
        } else {
            this.foodStarvationTimer = 0;
        }
        info.cancel();
    }
    */
}
