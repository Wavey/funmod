package com.me.funmod.ai;

import com.me.funmod.NetherGuy.NetherGuy;
import com.me.funmod.diamondzombie.DiamondZombie;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherGuyAI extends Goal {
    protected  NetherGuy mob;

    protected int teleportTimer;
    protected BlockPos teleportPos;
    protected World world;

    public NetherGuyAI(NetherGuy mob) {
        super();
        this.mob = mob;
        resetTimers();
    }
    protected void resetTimers() {
        
        this.teleportTimer = 0;
    }
    public void tick() {
        if

    }

    public boolean canStart() {
        return true;
    }
}
