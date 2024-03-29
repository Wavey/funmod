package com.me.funmod;

import com.me.funmod.NetherGuy.NetherGuyRenderer;
import com.me.funmod.diamondzombie.DiamondZombieRenderer;
import com.me.funmod.hunterIlliger.HunterilligerRenderer;
import com.me.funmod.mimic.MimicRenderer;
import com.me.funmod.projectiles.EntitySpawnPacket;
import com.me.funmod.projectiles.ZombieProjectileRenderer;
import com.me.funmod.rockzombie.RockZombieRenderer;
import com.me.funmod.spells.SpellProjectileEntityRenderer;
import com.me.funmod.wandstation.WandStationBlockScreen;
import com.me.funmod.wandstation.WandStationGuiDescription;
import com.me.funmod.zombie.NewZombieEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
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
        EntityRendererRegistry.register(FunMod.NEWZOMBIE, (context) -> {
            return new NewZombieEntityRenderer(context);
        });
        EntityRendererRegistry.register(FunMod.ROCKZOMBIE, ( context) -> {
            return new RockZombieRenderer(context);
        });
        EntityRendererRegistry.register(FunMod.DIAMONDZOMBIE, (context) -> {
            return new DiamondZombieRenderer(context);
        });
        EntityRendererRegistry.register(FunMod.SPELLPROJECTILEENTITY, ( context) -> {
            return new SpellProjectileEntityRenderer(context, context.getItemRenderer());
            //return new FlyingItemEntityRenderer<SpellProjectileEntity>(dispatcher, context.getItemRenderer());
        });
        EntityRendererRegistry.register(FunMod.ZOMBIEPROJECTILE, (context) -> {
            return new ZombieProjectileRenderer( context, context.getItemRenderer());
            //return new FlyingItemEntityRenderer<SpellProjectileEntity>(dispatcher, context.getItemRenderer());
        });
        EntityRendererRegistry.register(FunMod.HUNTERILLIGER, (context) -> {
            return new HunterilligerRenderer(context);
        });
        EntityRendererRegistry.register(FunMod.NETHERGUY, (context) -> {
            return new NetherGuyRenderer(context);
        });
        EntityRendererRegistry.register(FunMod.MIMIC, (context) -> {
            return new MimicRenderer(context);
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
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });

    }
}
