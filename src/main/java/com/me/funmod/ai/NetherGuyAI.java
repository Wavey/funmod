package com.me.funmod.ai;

import com.me.funmod.diamondzombie.DiamondZombie;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherGuyAI extends Goal {
    protected DiamondZombie mob;
    protected int decisionTimer;
    protected int teleportTimer;
    protected BlockPos teleportPos;
    protected World world;

    public NetherGuyAI(DiamondZombie mob) {
        super();
        this.mob = mob;
        resetTimers();
    }
    protected void resetTimers() {
        this.decisionTimer = 90;
        this.teleportTimer = 0;
    }
    public boolean canStart() {
        return true;
    }
}
