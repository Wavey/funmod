package com.me.funmod.mixins;

import com.me.funmod.FunMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity {
    //private static Random rand = new Random();

    private static final TrackedData<Boolean> MIMIC;

    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
    }

    static {
        MIMIC = DataTracker.registerData(AnimalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    protected void initDataTracker() {
        super.initDataTracker();

        AnimalEntity This = (AnimalEntity)(Object) this;
        boolean canbeMimic = This instanceof CowEntity || This instanceof SheepEntity || This instanceof PigEntity;
        boolean m = random.nextInt(5) == 1 && !this.isBaby() && canbeMimic;

        this.dataTracker.startTracking(MIMIC,m );
    }

    @Inject(at = @At("HEAD"), method = "mobTick()V", cancellable = true)
    private void onMobTick( CallbackInfo info) {
        AnimalEntity This = (AnimalEntity)(Object) this;
        if (this.dataTracker.get(this.MIMIC) == true && (this.getAttacker() instanceof PlayerEntity)){
            this.convertTo(FunMod.MIMIC,false);
        }
        if (This instanceof CowEntity && this.dataTracker.get(this.MIMIC) == true){
            this.goalSelector.clear();
            this.goalSelector.add(0, new SwimGoal(this));
            this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D));
            this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(new ItemConvertible[]{Items.WHEAT}), false));
            this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
            this.goalSelector.add(7, new LookAroundGoal(this));
            this.ambientSoundChance = 0;
        }
        if (This instanceof SheepEntity && this.dataTracker.get(this.MIMIC) == true){
            this.goalSelector.clear();
            this.goalSelector.add(0, new SwimGoal(this));
            this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
            this.goalSelector.add(3, new TemptGoal(this, 1.1D, Ingredient.ofItems(new ItemConvertible[]{Items.WHEAT}), false));
            this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
            this.goalSelector.add(8, new LookAroundGoal(this));
            this.ambientSoundChance = 0;
        }
        if (This instanceof PigEntity && this.dataTracker.get(this.MIMIC) == true){
            this.goalSelector.clear();
            this.goalSelector.add(0, new SwimGoal(this));
            this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
            this.goalSelector.add(4, new TemptGoal(this, 1.2D, Ingredient.ofItems(new ItemConvertible[]{Items.CARROT_ON_A_STICK}), false));
            this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
            this.goalSelector.add(8, new LookAroundGoal(this));
            this.ambientSoundChance = 0;
        }
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
