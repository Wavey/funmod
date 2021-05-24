package com.me.funmod;

import com.me.funmod.diamondzombie.DiamondZombieRenderer;
import com.me.funmod.projectiles.EntitySpawnPacket;
import com.me.funmod.projectiles.ZombieProjectileRenderer;
import com.me.funmod.rockzombie.RockZombieRenderer;
import com.me.funmod.spells.SpellProjectileEntity;
import com.me.funmod.spells.SpellProjectileEntityRenderer;
import com.me.funmod.wandstation.WandStationBlock;
import com.me.funmod.wandstation.WandStationBlockScreen;
import com.me.funmod.wandstation.WandStationGuiDescription;
import com.me.zombie.NewZombieEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class FunClientMod implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(FunMod.ModID, "spawn_packet");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(FunMod.NEWZOMBIE, (dispatcher, context) -> {
            return new NewZombieEntityRenderer(dispatcher);
        });
        EntityRendererRegistry.INSTANCE.register(FunMod.ROCKZOMBIE, (dispatcher, context) -> {
            return new RockZombieRenderer(dispatcher);
        });
        EntityRendererRegistry.INSTANCE.register(FunMod.DIAMONDZOMBIE, (dispatcher, context) -> {
            return new DiamondZombieRenderer(dispatcher);
        });
        EntityRendererRegistry.INSTANCE.register(FunMod.SPELLPROJECTILEENTITY, (dispatcher, context) -> {
            return new SpellProjectileEntityRenderer(dispatcher, context.getItemRenderer());
            //return new FlyingItemEntityRenderer<SpellProjectileEntity>(dispatcher, context.getItemRenderer());
        });
        EntityRendererRegistry.INSTANCE.register(FunMod.ZOMBIEPROJECTILE, (dispatcher, context) -> {
            return new ZombieProjectileRenderer(dispatcher, context.getItemRenderer());
            //return new FlyingItemEntityRenderer<SpellProjectileEntity>(dispatcher, context.getItemRenderer());
        });

        ScreenRegistry.<WandStationGuiDescription, WandStationBlockScreen>register(FunMod.SCREEN_HANDLER_TYPE,
                (gui, inventory, title) -> new WandStationBlockScreen(gui, inventory.player, title));

        receiveEntityPacket();
    }

    public void receiveEntityPacket() {
        ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.pitch = pitch;
                e.yaw = yaw;
                e.setEntityId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });

    }
}
