package com.me.funmod;

import com.me.funmod.rockzombie.RockZombieRenderer;
import com.me.zombie.NewZombieEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class FunClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(FunMod.NEWZOMBIE, (dispatcher, context) -> {
            return new NewZombieEntityRenderer(dispatcher);
        });
        EntityRendererRegistry.INSTANCE.register(FunMod.ROCKZOMBIE, (dispatcher, context) -> {
            return new RockZombieRenderer(dispatcher);
        });
    }
}
