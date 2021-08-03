package com.me.funmod.mixins;

import com.me.funmod.rockzombie.RockZombie;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(ZombieEntity.class)
public class RockZombiemixin {
    @Shadow
    public World world;


    @Redirect(method = "tick",
             at = @At(value = "INVOKE", target="Lnet/minecraft/entity/mob/ZombieEntity;isConvertingInWater()Z"))
    private boolean onTick(ZombieEntity entity) {
        if (this.world.getRegistryKey() == World.NETHER){


            RockZombie.convertToRockZombie(entity);

        }
            return entity.isConvertingInWater();
            //entity.tick();

    }


}
