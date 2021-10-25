package com.me.funmod.hunterIlliger;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Hunterilliger extends PillagerEntity {
    public Hunterilliger(EntityType<? extends PillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    public void shoot(LivingEntity entity, LivingEntity target, ProjectileEntity projectile, float multishotSpray, float speed){
        ((ArrowEntity)projectile).addEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 2));
        ((ArrowEntity)projectile).addEffect(new StatusEffectInstance(StatusEffects.POISON, 100));

        double d = target.getX() - entity.getX();
        double e = target.getZ() - entity.getZ();
        double f = Math.sqrt(d * d + e * e);
        double g = target.getBodyY(0.3333333333333333D) - projectile.getY() + f * 0.20000000298023224D;
        Vec3f vector3f = this.getProjectileLaunchVelocity(entity, new Vec3d(d, g, e), multishotSpray);
        projectile.setVelocity((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ(), speed, (float)(14 - entity.world.getDifficulty().getId() * 4));
        entity.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
    }
    public static DefaultAttributeContainer.Builder createHostileAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE,75).add(
        EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);


    }

    @Override
    public boolean canLead() {
        return false;
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {


        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }
    protected void initGoals() {
        super.initGoals();


    }

    public int getLimitPerChunk() {
        return 5;
    }

}
