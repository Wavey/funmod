package com.me.mixins;

import com.me.funmod.FunMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class BiomeFeaturesMixin {
    @Inject(at = @At("RETURN"), method = "addMonsters(Lnet/minecraft/world/biome/SpawnSettings$Builder;III)V")
    private static void addMonsters(SpawnSettings.Builder builder, int zw, int zv, int skel, CallbackInfo info) {
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(FunMod.NEWZOMBIE, zw / 4, 1, 1));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(FunMod.HUNTERILLIGER, 50, 4, 5));
        System.out.println("Adding newzombie to spawnsettings with weight " + zw);
    }

}
