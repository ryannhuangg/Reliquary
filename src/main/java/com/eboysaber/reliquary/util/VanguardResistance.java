package com.eboysaber.reliquary.util;

import com.eboysaber.reliquary.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "reliquary")
public final class VanguardResistance {
    public VanguardResistance () {
    }

    @SubscribeEvent 
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        boolean hasBrace = CuriosUtil.isEquipped(player, ModItems.VANGUARD_BRACE.get());
        boolean hasShield = CuriosUtil.isEquipped(player, ModItems.VANGUARD_SHIELD.get());

        if (player.getHealth() < 8.0f) {
            if (hasBrace) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 19, 0, true, false, true));
            if (hasShield) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 19, 1, true, false, true));
        }
    }
}
