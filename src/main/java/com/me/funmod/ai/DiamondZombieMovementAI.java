package com.me.funmod.ai;

import com.me.funmod.diamondzombie.DiamondZombie;
import com.me.funmod.projectiles.ZombieProjectile;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.sound.SoundEvents;

import java.time.Clock;
import java.util.Random;

public class DiamondZombieMovementAI extends ZombieAttackGoal {
    private final DiamondZombie diamondZombie;
    public int shoottimer;
    public int metashoottimer;
    public boolean side = true;
    public int switchTimer = 7;
    private static final Random random = new Random(Clock.systemDefaultZone().instant().getNano());


    public DiamondZombieMovementAI(DiamondZombie diamondZombie, double speed, boolean pauseWhenMobIdle, DiamondZombie diamondZombie1) {

        super(diamondZombie, speed, pauseWhenMobIdle);

        this.diamondZombie = diamondZombie1;
    }
    public void switchSides(){
        System.out.println("switching");
        if(side == true){
            side = false;
        }else{
            side = true;
        }

    }

    public void resetTimer(){
        shoottimer = 30;
        metashoottimer = 5;
    }

    public void tick(){
        if (switchTimer <= 0) {
            switchSides();
            switchTimer = 7;
        }
        switchTimer --;
      PlayerEntity nearestPlayer = this.mob.world.getClosestPlayer(this.mob.getX(), this.mob.getY(), this.mob.getZ(), 100, false);
      double d = this.mob.squaredDistanceTo(nearestPlayer.getX(), nearestPlayer.getY(), nearestPlayer.getZ());
      shoottimer --;
      if(shoottimer == 10){
          this.mob.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 3,1);
      }
      if(shoottimer <= 0){
          System.out.print("shoot");
          if(metashoottimer > 0) {
              metashoottimer--;
              this.mob.lookAtEntity(nearestPlayer,360,360);
              ZombieProjectile spellProjectile = new ZombieProjectile(this.mob.world, this.diamondZombie);
              spellProjectile.setProperties(this.diamondZombie, this.diamondZombie.pitch, this.diamondZombie.yaw, 0.0F, 1.5F, 1.0F);
              spellProjectile.setNoGravity(true);
              this.mob.playSound(SoundEvents.ENTITY_BLAZE_SHOOT,1,1);
              this.diamondZombie.world.spawnEntity(spellProjectile);
          }else{
              resetTimer();
          }
      }
      if(d < 20){

          this.mob.lookAtEntity(nearestPlayer,360,360);
          this.diamondZombie.getNavigation().stop();
          if(side == true) {
              this.mob.getMoveControl().strafeTo(-1F, 0.3f);
              System.out.print("side 1");
          }
          if (side == false) {
              this.mob.getMoveControl().strafeTo(-1F, -0.3f);
              System.out.print("side 2");
          }





      }else{

      }

  }

}
