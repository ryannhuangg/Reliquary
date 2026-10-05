package com.eboysaber.reliquary.util;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.chat.Component;
import com.eboysaber.reliquary.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "reliquary")
public final class EffigyOfCondemned {

    private EffigyOfCondemned() {
    }

    private static final Map<UUID, Integer> doomtimer = new HashMap <>();
    private static final Map<UUID, Integer> cooldowntimer = new HashMap <>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return; //desync check
        
        UUID id = player.getUUID();

        if (cooldowntimer.containsKey(id)) {
            int cooldownticksLeft = cooldowntimer.get(id) - 1;
            if (cooldownticksLeft <= 0) cooldowntimer.remove(id);
            else cooldowntimer.put(id, cooldownticksLeft);
        }

        if (doomtimer.containsKey(id)) {
            int ticksLeft = doomtimer.get(id) - 1;

            if (ticksLeft > 20 && ticksLeft % 20 == 0) player.level().playSound(null, player.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
            else if (ticksLeft == 20) player.level().playSound(null, player.blockPosition(), SoundEvents.WITHER_SHOOT, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 0.5f);

            if (ticksLeft <= 0) {
                player.level().playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
                doomtimer.remove(id);
                player.kill();
            }
            else doomtimer.put(id, ticksLeft);
        }
    }

    @SubscribeEvent
    public static void onTargetKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player killer) {
            if (killer.level().isClientSide()) return; //desync check

            UUID killerId = killer.getUUID();

            if (doomtimer.containsKey(killerId)) doomtimer.remove(killerId);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) { //ensures its a player dying
            if (player.level().isClientSide()) return; //desync check

        boolean hasEffigy = CuriosUtil.isEquipped(player, ModItems.EFFIGY_OF_CONDEMNED.get());

            if (hasEffigy && !cooldowntimer.containsKey(player.getUUID())) {
                event.setCanceled(true);

                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(10, 40, 10));
                    serverPlayer.connection.send(new ClientboundSetTitleTextPacket(
                        Component.literal("\u00A74\u00A7lDOOMED.")
                    ));
                    serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(
                        Component.literal("\u00A74\u00A7l\u00A7kXXX \u00A74\u00A7lBLOOD PACT SIGNED. \u00A74\u00A7l\u00A7kXXX ")
                    ));
                }

                player.level().playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, net.minecraft.sounds.SoundSource.PLAYERS, 0.8f, 0.5f);
                player.level().playSound(null, player.blockPosition(), SoundEvents.ANVIL_LAND, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 0.8f);

                player.setHealth(3.0f);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 1, true, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, true, false, true));
                doomtimer.put(player.getUUID(), 200);
                cooldowntimer.put(player.getUUID(), 300); //15s cooldown for playtesting
            }
        }
    }
}
