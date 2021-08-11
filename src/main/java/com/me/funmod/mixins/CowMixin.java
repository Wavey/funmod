package com.me.funmod.mixins;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Random;

@Mixin(CowEntity.class)
public abstract class CowMixin extends AnimalEntity {

    private static Random random = new Random();

    private static final TrackedData<Boolean> MIMIC;

    protected CowMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    static {
        MIMIC = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(MIMIC, random.nextInt(5) == 1);
    }

    @Inject(at = @At("HEAD"), method = "getAmbientSound()Lnet/minecraft/sound/SoundEvent;", cancellable = true)
    protected void getAmbientSound(CallbackInfoReturnable<SoundEvent> info) {
        if (this.dataTracker.get(this.MIMIC) == true){
            info.setReturnValue(SoundEvents.ENTITY_BEE_DEATH);
        }else{
            info.setReturnValue((SoundEvents.ENTITY_COW_AMBIENT));
        }

    }

}
