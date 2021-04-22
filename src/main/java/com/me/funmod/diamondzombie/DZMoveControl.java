package com.me.funmod.diamondzombie;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;

public class DZMoveControl extends MoveControl {
    public DZMoveControl(MobEntity entity) {
        super(entity);
    }

    public void strafeTo(float forward, float sideways) {

            this.state = MoveControl.State.STRAFE;
            this.forwardMovement = forward;
            this.sidewaysMovement = sideways;
            this.speed = 3f;

    }
}
