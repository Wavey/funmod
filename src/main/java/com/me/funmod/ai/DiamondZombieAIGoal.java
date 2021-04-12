package com.me.funmod.ai;

import com.me.funmod.diamondzombie.DiamondZombie;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DiamondZombieAIGoal extends Goal {

    protected DiamondZombie mob;
    protected int decisionTimer;
    protected int teleportTimer;
    protected BlockPos teleportPos;
    protected World world;
    public DiamondZombieAIGoal(DiamondZombie mob) {
        super();
        this.mob = mob;
        resetTimers();
    }

    protected void resetTimers() {
        this.decisionTimer = 60;
        this.teleportTimer = 0;
    }

    public boolean canStart() {
        return true;
    }

    public void tick() {
        if(this.teleportTimer > 0) {
            this.teleportTimer --;
            if(this.teleportTimer == 0) {
                teleportTime();
            }
            return;
        }

        this.decisionTimer --;
        if(this.decisionTimer <= 0) {
            System.out.println("Making a decision");
            resetTimers();
            findPlayer();
        }

    }


    protected void findPlayer() {
        PlayerEntity nearestPlayer = this.mob.world.getClosestPlayer(this.mob.getX(), this.mob.getY(), this.mob.getZ(), 100, false);
        if( nearestPlayer != null) {
            System.out.println("Found player... will teleport soon");
            this.teleportPos = nearestPlayer.getBlockPos();

            this.teleportTimer = 30;
            this.mob.sendPlayerSpawnEffect();
        }else{
            this.mob.canImmediatelyDespawn(100);
        }
    }

    public void teleportTime(){
        System.out.println("teleporting");
        this.mob.teleport(this.teleportPos.getX(), this.teleportPos.getY(), this.teleportPos.getZ());
        resetTimers();

    }

}
