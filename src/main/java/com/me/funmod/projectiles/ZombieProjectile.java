package com.me.funmod.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ZombieProjectile extends ThrownItemEntity {
    public ZombieProjectile(EntityType<? extends ThrownItemEntity>entityType, World world){
        super(entityType, world);
    }
    public ZombieProjectile(World world, LivingEntity owner) {
        super(null, owner, world); // null will be changed later
    }

    public ZombieProjectile(World world, double x, double y, double z) {
        super(null, x, y, z, world); // null will be changed later
    }
    protected Item getDefaultItem(){
        return null;
    }
    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()),3.f);
        entity.playSound(SoundEvents.ITEM_FIRECHARGE_USE,1f,1f);
    }
    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        this.remove();

    }

}
