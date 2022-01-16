package com.me.funmod.ai;

import com.me.funmod.NetherGuy.NetherGuy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;


public class NetherGuyTargetGoal<T extends LivingEntity> extends FollowTargetGoal {



    public NetherGuyTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility) {
        this(mob, targetClass, checkVisibility, false);
    }

    public NetherGuyTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, boolean checkCanNavigate) {
        this(mob, targetClass, 10, checkVisibility, checkCanNavigate, (Predicate)null);
    }

    public NetherGuyTargetGoal(MobEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate);
        resettimers();
    }
    //protected NetherGuy mob;
    protected BlockPos teleportPos;
    protected int teleportTimer =90;
    protected int visibilitycountdown = 500;
    protected int distancecountdown = 100;
    protected int navigatecountdown = 100;



    protected void resettimers(){
        teleportTimer = 90;
        visibilitycountdown = 500;
        distancecountdown = 100;
        navigatecountdown = 100;
    }
    protected void teleport(){
        System.out.println("teleporting");
        ((NetherGuy)this.mob).doTeleport(teleportPos);
        this.mob.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT,2,1);
        resettimers();

    }

    protected NetherGuy getNetherGuy() {
        return (NetherGuy) this.mob;
    }

    protected void teleporttick(){
        if(this.teleportTimer > 0) {
            this.teleportTimer --;
            if(this.teleportTimer == 0) {
                teleport();
            }
        }
    }
    public void tick(){
        super.tick();
        teleportPos = targetEntity.getBlockPos();
        if (!this.mob.getVisibilityCache().canSee(targetEntity)) {
            visibilitycountdown--;
        }else{
            visibilitycountdown ++;
        }
        if (visibilitycountdown <= 0) {
            teleporttick();
        }
        if (this.mob.getNavigation().findPathTo(targetEntity, 256) == null) {
            navigatecountdown--;
        }
        if (navigatecountdown <= 0) {
            teleporttick();
        }
        getNetherGuy().setTeleportPos(teleportPos);
        getNetherGuy().setTeleportTimer(teleportTimer);
    }

}
