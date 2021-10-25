package com.me.funmod.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity {
    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
    }
    @Nullable
    public <T extends AnimalEntity> T convertAnimalTo(EntityType<T> entityType, boolean keepEquipment) {
        if (this.isRemoved()) {
            return null;
        } else {
            T animalEntity = entityType.create(this.world);
            animalEntity.copyPositionAndRotation(this);
            animalEntity.setBaby(this.isBaby());
            animalEntity.setAiDisabled(this.isAiDisabled());
            if (this.hasCustomName()) {
                animalEntity.setCustomName(this.getCustomName());
                animalEntity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistent()) {
                animalEntity.setPersistent();
            }

            animalEntity.setInvulnerable(this.isInvulnerable());
            if (keepEquipment) {
                animalEntity.setCanPickUpLoot(this.canPickUpLoot());
                EquipmentSlot[] var4 = EquipmentSlot.values();
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    EquipmentSlot equipmentSlot = var4[var6];
                    ItemStack itemStack = this.getEquippedStack(equipmentSlot);
                    if (!itemStack.isEmpty()) {
                        animalEntity.equipStack(equipmentSlot, itemStack.copy());
                        animalEntity.setEquipmentDropChance(equipmentSlot, this.getDropChance(equipmentSlot));
                        itemStack.setCount(0);
                    }
                }
            }

            this.world.spawnEntity(animalEntity);
            if (this.hasVehicle()) {
                Entity entity = this.getVehicle();
                this.stopRiding();
                animalEntity.startRiding(entity, true);
            }

            this.discard();
            return animalEntity;
        }
    }
}
