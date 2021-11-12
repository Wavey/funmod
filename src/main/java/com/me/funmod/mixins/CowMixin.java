package com.me.funmod.mixins;


import com.me.funmod.FunMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Random;
import com.me.funmod.mimic.Mimic;

@Mixin(CowEntity.class)
public abstract class CowMixin extends AnimalEntity {

  //  private static Random random = new Random();

  // private static final TrackedData<Boolean> MIMIC;

    protected CowMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    //static {
       // MIMIC = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    //}
  //  protected void initDataTracker() {
     //   super.initDataTracker();

     //   this.dataTracker.startTracking(MIMIC, random.nextInt(5) == 1 && !this.isBaby());
   // }
    //@Inject(at = @At("HEAD"), method = "tick()V", cancellable = true)
   // private void onTick( CallbackInfo info) {
    //    if (this.dataTracker.get(this.MIMIC) == true && (this.getAttacker() instanceof PlayerEntity)){
     //       this.convertTo(FunMod.MIMIC,false);
     //   }
   // }

   // @Inject(at = @At("HEAD"), method = "initGoals()V", cancellable = true)
   // protected void initGoals(CallbackInfo  info) {
       // this.goalSelector.add(0, new SwimGoal(this));
      //  this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D));
      //  this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(new ItemConvertible[]{Items.WHEAT}), false));
       // this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
       // this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
       // if(!this.dataTracker.get(this.MIMIC)){this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));}
       // this.goalSelector.add(7, new LookAroundGoal(this));
    //}


    //@Inject(at = @At("HEAD"), method = "getAmbientSound()Lnet/minecraft/sound/SoundEvent;", cancellable = true)
    //protected void getAmbientSound(CallbackInfoReturnable<SoundEvent> info) {
      //  if (this.dataTracker.get(this.MIMIC) == true){
       //     this.goalSelector.clear();
        //    this.goalSelector.add(0, new SwimGoal(this));
        //    this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4D));
        //    this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(new ItemConvertible[]{Items.WHEAT}), false));
         //   this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
        //    this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
         //   this.goalSelector.add(7, new LookAroundGoal(this));
       //     info.setReturnValue(SoundEvents.ENTITY_BEE_DEATH);
      //  }else{
       //     info.setReturnValue((SoundEvents.ENTITY_COW_AMBIENT));
      //  }

    }

//}
