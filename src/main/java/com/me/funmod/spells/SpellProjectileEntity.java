package com.me.funmod.spells;

import com.me.funmod.FunMod;
import com.me.funmod.projectiles.EntitySpawnPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class SpellProjectileEntity  extends ThrownItemEntity implements FlyingItemEntity {
    public SpellProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpellProjectileEntity(World world, LivingEntity owner) {
        super(FunMod.SPELLPROJECTILEENTITY, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return FunMod.SPELL;
    }

    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, FunMod.PacketID);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.remove();
        }
    }

}
