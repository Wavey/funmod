package com.me.funmod.mixins;

import com.me.funmod.rockzombie.RockZombie;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(ZombieEntity.class)
public abstract class RockZombiemixin extends HostileEntity {
    //@Shadow
    //public World world;

    public RockZombiemixin(EntityType<ZombieEntity> zombie, World world) {
        super(EntityType.ZOMBIE, world);
    }

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
