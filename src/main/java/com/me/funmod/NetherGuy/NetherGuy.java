package com.me.funmod.NetherGuy;

import com.me.funmod.FunMod;
import com.me.funmod.ai.NetherGuyTargetGoal;
import com.me.funmod.general.PlayerEntityNetherInterface;
import com.me.funmod.mixins.PlayerEntityMixin;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Random;

public class NetherGuy extends WitherSkeletonEntity {
    public NetherGuy(EntityType<? extends NetherGuy> entityType, World world) {
        super(entityType, world);
        if(!this.world.isClient) {
            // keep track of how many of us are spawned
            addToCount(this);
            System.out.println("Nether guy created");
        }
    }
    private static Random random = new Random();
    public static DefaultAttributeContainer.Builder createNetherGuyAttributes() {
        return AbstractSkeletonEntity.createAbstractSkeletonAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE,75).add(
                EntityAttributes.GENERIC_MOVEMENT_SPEED, .3).add(
                EntityAttributes.GENERIC_MAX_HEALTH, 125d).add(
                EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5d);



    }
    @Override
    protected void initGoals() {

        this.goalSelector.add(2,
                new NetherGuyTargetGoal<PlayerEntity>(this, PlayerEntity.class,10,
                                        false,false, new PlayerPredicate()));

    }

    protected static ArrayList<WeakReference<NetherGuy>> netherGuys = new ArrayList<WeakReference<NetherGuy>>();
    protected static final int MAX_GUYS_AT_ONCE = 1;



    protected static int countNetherGuys() {
        netherGuys.removeIf((e) -> {
            NetherGuy guy = e.get();
            if(guy != null) {
                return guy.isDead();
            }
            return true;
        });
        return netherGuys.size();
    }

    protected static boolean canSpawnNetherGuy() {
        int numNetherGuys = countNetherGuys();
        if(numNetherGuys < MAX_GUYS_AT_ONCE) {
            return true;
        }
        // we shouldn't get this, but there is something up with switching between dimensions
        while(netherGuys.size() > MAX_GUYS_AT_ONCE) {
            netherGuys.remove(0);
        }
        return false;

    }


    public static int findlocation(int pos){
       int r = random.nextInt(16) + 16;
        if (random.nextInt(2) == 0){
            r = r*-1;


        }
        return r + pos;
    }

    public static int findlocationY(int pos){
        int r = random.nextInt(10);
        if (random.nextInt(2) == 0){
            r = r*-1;


        }
        return r + pos;
    }
    // Handle spawning
    public static boolean spawnNewGuy(LivingEntity player) {
        if (canSpawnNetherGuy() ) {

            BlockPos pos = player.getBlockPos();
            int y = findlocationY(pos.getY());
            int x = findlocation(pos.getX());
            int z = findlocation(pos.getZ());

            BlockPos pos2 = new BlockPos(x,y,z);
            if( (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, player.world, pos2, EntityType.WITHER_SKELETON))){
                NetherGuy guy = FunMod.NETHERGUY.create(player.world);
                guy.updatePosition(pos2.getX(), pos2.getY(), pos2.getZ());

                player.world.spawnEntity(guy);
                System.out.println("Spawning new Nether guy");
                return true;
            }else{
                System.out.println("failed to find spawn point");

                return false;
            }

        }

        return false;
    }

    static protected void addToCount(NetherGuy guy) {
        netherGuys.add(new WeakReference<NetherGuy>(guy));
    }




    public class PlayerPredicate implements Predicate<LivingEntity> {
        public boolean test (LivingEntity t) {
            PlayerEntityNetherInterface player = (PlayerEntityNetherInterface) t;
            if(player == null) {
                return false;
            }
            int nethertimer = player.getNetherTimer();
            //System.out.printf("Checking player entity with nether timer of %d \n", nethertimer);
            if(nethertimer > 0) {
                //System.out.println("Not adding");
                return false;
            }
            //System.out.println("Adding");
            return true;
        }
    }



}
