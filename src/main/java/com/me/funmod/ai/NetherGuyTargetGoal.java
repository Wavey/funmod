package com.me.funmod.ai;

import com.me.funmod.NetherGuy.NetherGuy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;


public class NetherGuyTargetGoal extends Goal {
    protected NetherGuy netherGuy;
    protected @Nullable Predicate<Entity> targetPredicate;


    public NetherGuyTargetGoal(NetherGuy guy,BlockPos savedPos,@Nullable Predicate<Entity> targetPredicate) {
        super();
        netherGuy = guy;
        this.targetPredicate = targetPredicate;
        this.savedPos = savedPos;
        resettimers();
    }
    //protected NetherGuy mob;
    protected BlockPos teleportPos;
    protected int teleportTimer =90;
    protected int visibilitycountdown = 500;
    protected int navigatecountdown = 100;
    protected int noTargetCountdown;
    protected int stuckCountdown;
    protected BlockPos savedPos;




    protected void resettimers(){
        teleportTimer = 60;
        visibilitycountdown = 500;
        navigatecountdown = 100;
        noTargetCountdown = 100;
        stuckCountdown = 80;
    }
    protected void teleport(){
        System.out.println("teleporting");
        netherGuy.doTeleport(teleportPos);
        netherGuy.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT,2,1);
        resettimers();

    }
    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public boolean canStop() {
        return false;
    }

    public void stop() {
        System.out.println("Nether teleport goal stopping");
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

        LivingEntity target = netherGuy.getTarget();

        if (target == null) {
            // There is no target, so look for a valid player
            PlayerEntity nearestPlayer = netherGuy.world.getClosestPlayer(
                    netherGuy.getX(), netherGuy.getY(), netherGuy.getZ(), 100000.0, targetPredicate);
            if (nearestPlayer != null) {
                teleportPos = nearestPlayer.getBlockPos();
                teleporttick();
            }
        }
        else {
            teleportPos = target.getBlockPos();
            if (netherGuy.getNavigation().findPathTo(target, 256) == null) {
                if (navigatecountdown <= 0) {
                    teleporttick();
                }
                else {
                    navigatecountdown--;
                }
            }
            else {
                if(netherGuy.getBlockPos() == savedPos){
                    stuckCountdown--;
                    if(stuckCountdown <= 0){
                        System.out.println("stuck teleport activate");
                        teleporttick();
                    }
                    System.out.println("stuck" + stuckCountdown);
                }
                else {
                    // Everything is working fine
                    resettimers();
                }
            }
        }
        savedPos = this.netherGuy.getBlockPos();
        netherGuy.setTeleportPos(teleportPos);
        netherGuy.setTeleportTimer(teleportTimer);
    }

}
