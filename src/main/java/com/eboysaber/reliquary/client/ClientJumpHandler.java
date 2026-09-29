package com.eboysaber.reliquary.client;

import com.eboysaber.reliquary.network.ExtraJumpPayload;
import com.eboysaber.reliquary.util.BottleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "reliquary", value = Dist.CLIENT)
public final class ClientJumpHandler {

    private static int jumpsUsed = 0;
    private static boolean wasDown = false;
    private static boolean wasOnGround = false;

    private ClientJumpHandler() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        boolean down = minecraft.options.keyJump.isDown();
        boolean pressed = down && !wasDown;
        wasDown = down;

        boolean groundedLastTick = wasOnGround;
        wasOnGround = player.onGround();

        if (wasOnGround || player.isInWater() || player.isInLava()
                || player.onClimbable() || player.isPassenger()) {
            jumpsUsed = 0;
            return;
        }

        if (!pressed || groundedLastTick || minecraft.screen != null) {
            return;
        }
        if (player.getAbilities().flying || player.isFallFlying()) {
            return;
        }
        if (jumpsUsed >= BottleUtil.count(player)) {
            return;
        }

        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x, player.getAttributeValue(Attributes.JUMP_STRENGTH), motion.z);
        player.fallDistance = 0;
        jumpsUsed++;
        PacketDistributor.sendToServer(new ExtraJumpPayload());
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        jumpsUsed = 0;
        wasDown = false;
        wasOnGround = false;
    }
}
