package com.me.funmod.ai;

import com.me.funmod.mimic.Mimic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;


import java.util.EnumSet;

public class MimicIgniteGoal extends Goal {
    private final Mimic mimic;
    private LivingEntity target;

    public MimicIgniteGoal(Mimic mimic) {
        this.mimic = mimic;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.mimic.getTarget();
        return this.mimic.getFuseSpeed() > 0 || livingEntity != null && this.mimic.squaredDistanceTo(livingEntity) < 9.0D;
    }

    public void start() {
        this.mimic.getNavigation().stop();
        this.target = this.mimic.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.mimic.setFuseSpeed(-1);
        } else if (this.mimic.squaredDistanceTo(this.target) > 49.0D) {
            this.mimic.setFuseSpeed(-1);
        } else if (!this.mimic.getVisibilityCache().canSee(this.target)) {
            this.mimic.setFuseSpeed(-1);
        } else {
            this.mimic.setFuseSpeed(3);
        }
    }
}
