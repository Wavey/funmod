package com.me.funmod.general;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

public interface PlayerEntityNetherInterface {
    public int getNetherTimer();
}
