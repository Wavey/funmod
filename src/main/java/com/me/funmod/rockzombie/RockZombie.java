package com.me.funmod.rockzombie;

import com.me.funmod.FunMod;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import org.jetbrains.annotations.Nullable;

public class RockZombie extends ZombieEntity {
    public RockZombie(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        {
        }
    }

    public static void convertToRockZombie(ZombieEntity entity) {
        if(!(entity instanceof RockZombie)) {
            ZombieEntity zombieEntity = (ZombieEntity)entity.method_29243(FunMod.ROCKZOMBIE, true);
        }

    }
    public void tick() {
        diamondConvert();
        super.tick();
    }
    public void diamondConvert(){
        ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.DIAMOND));
        if(itemStack.getItem() == Items.DIAMOND){
            convertTo(FunMod.DIAMONDZOMBIE);
        }
    }
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        super.initialize(world,difficulty,spawnReason,entityData,entityTag);
        this.setCanPickUpLoot(true);
        return entityData;
    }


    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        return ZombieEntity.createZombieAttributes().add(
                EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D).add (

                EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D).add(
                EntityAttributes.GENERIC_ARMOR, 2.0D).add(
                EntityAttributes.GENERIC_MAX_HEALTH, 40.0d).add(
                EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5d).add(
                EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22d).add(
                EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);

    }
}