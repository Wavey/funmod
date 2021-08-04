package com.me.funmod.mixins;

import com.me.funmod.general.PlayerEntityNetherInterface;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityNetherInterface {
    //public PlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
    PlayerEntityMixin(EntityType<? extends PlayerEntity> entity, World world) {
        super( entity, world);
    }
    public int nethertimer;
    private static TrackedData<Integer> NETHER_TIMER;
    private void nethertimerticksubtract(){
        int nethertimer = this.getNetherTimer();
        if (nethertimer > 0){
            this.setNetherTimer(nethertimer - 1);
        }
    }
    private void nethertimertickadd(){
        int nethertimer = this.getNetherTimer();
        if (nethertimer < 1000){
            this.setNetherTimer(nethertimer + 4);
        }
    }

    static {
        NETHER_TIMER = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public int getNetherTimer() {
        return (Integer)this.dataTracker.get(NETHER_TIMER);
    }
    public void setNetherTimer(int value) {
        this.dataTracker.set(NETHER_TIMER, value);
    }

    @Inject(at = @At("HEAD"), method = "tick()V", cancellable = true)
    private void onTick( CallbackInfo info) {

        if(!this.world.isClient) {
            if (this.world.getRegistryKey() == World.NETHER) {
                nethertimerticksubtract();

                //System.out.println(nethertimer);
            }else{
                nethertimertickadd();
            }
        }
    }
    @Inject(at = @At("RETURN"), method = "canFoodHeal()Z", cancellable = true)
    private void onCanFoodHeal(CallbackInfoReturnable<Boolean> info) {
        if(info.getReturnValue()) {
            float health = this.getHealth();
            boolean retval = health < 9.5f || health >= 10.5f;
            //System.out.println("Health = " + health + ", retval = " + retval);
            info.setReturnValue(retval);
        }
    }

    @Inject(at = @At("HEAD"), method = "initDataTracker()V", cancellable = true)
    protected void initDataTracker(CallbackInfo info) {
        this.dataTracker.startTracking(NETHER_TIMER, 0);
    }



}
