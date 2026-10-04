package com.eboysaber.reliquary.util;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
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
            if (ticksLeft <= 0) {
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
                player.setHealth(3.0f);
                player.setAbsorptionAmount(4.0f);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, true, false, true));
                doomtimer.put(player.getUUID(), 200);
                cooldowntimer.put(player.getUUID(), 6000); //30s cooldown for now 
            }
        }
    }
}
