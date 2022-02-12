package com.me.funmod.NetherGuy;

import com.me.funmod.FunMod;
import com.me.funmod.ai.NetherGuyTargetGoal;
import com.me.funmod.general.PlayerEntityNetherInterface;
import com.me.funmod.mixins.PlayerEntityMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
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
    protected static TrackedData<BlockPos> TELEPORT_POS;
    protected static TrackedData<Integer> TELEPORT_TIMER;

    public static DefaultAttributeContainer.Builder createNetherGuyAttributes() {
        return AbstractSkeletonEntity.createAbstractSkeletonAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE,75).add(
                EntityAttributes.GENERIC_MOVEMENT_SPEED, .3).add(
                EntityAttributes.GENERIC_MAX_HEALTH, 125d).add(
                EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5d);



    }
    public void doTeleport(BlockPos pos ){
        teleport(pos.getX(), pos.getY(), pos.getZ());
        setPosition(pos.getX(),pos.getY(), pos.getZ());
    }
    @Override
    protected void initGoals() {
        super.initGoals();
        goalSelector.add(1, new NetherGuyTargetGoal(this, new PlayerPredicate()));
    }


    protected static ArrayList<WeakReference<NetherGuy>> netherGuys = new ArrayList<WeakReference<NetherGuy>>();
    protected static final int MAX_GUYS_AT_ONCE = 1;
    private static final Random staticRandom = new Random();


    public void setTeleportPos(BlockPos pos) {
        dataTracker.set(TELEPORT_POS, pos);
    }

    public BlockPos getTeleportPos() {
        return dataTracker.get(TELEPORT_POS);
    }

    public void setTeleportTimer(int timer) {
        dataTracker.set(TELEPORT_TIMER, timer);
    }

    public int getTeleportTimer() {
        return dataTracker.get(TELEPORT_TIMER);
    }

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
       int r = staticRandom.nextInt(16) + 16;
        if (staticRandom.nextInt(2) == 0){
            r = r*-1;


        }
        return r + pos;
    }

    public static int findlocationY(int pos){
        int r = staticRandom.nextInt(10);
        if (staticRandom.nextInt(2) == 0){
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
                guy.setPosition(pos2.getX(), pos2.getY(), pos2.getZ());

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

    static {
        TELEPORT_POS = DataTracker.registerData(NetherGuy.class, TrackedDataHandlerRegistry.BLOCK_POS);
        TELEPORT_TIMER = DataTracker.registerData(NetherGuy.class, TrackedDataHandlerRegistry.INTEGER);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(TELEPORT_POS, new BlockPos(0,0,0));
        dataTracker.startTracking(TELEPORT_TIMER, 100);
    }

    public void tick() {
        super.tick();

        if(world.isClient) {
            checkTeleportParticles();
        }
    }


    @Environment(EnvType.CLIENT)
    protected void checkTeleportParticles() {
        if(getTeleportTimer() < 50) {
            produceTeleportParticles();
        }
    }

    protected void produceTeleportParticles() {
        ParticleEffect parameters = ParticleTypes.DRAGON_BREATH;
        BlockPos pos = getTeleportPos();

        for(int i = 0; i < 10; ++i) {
            double d = random.nextGaussian() * 0.02D;
            double e = random.nextGaussian() * 0.02D;
            double f = random.nextGaussian() * 0.02D;
            this.world.addParticle(parameters, pos.getX(), pos.getY(), pos.getZ(), d, e, f);
        }
    }


    public static class PlayerPredicate implements Predicate<Entity> {
        public boolean test (Entity t) {
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
