package com.eboysaber.reliquary.util;

import com.eboysaber.reliquary.ReliquaryMod;
import com.eboysaber.reliquary.item.ModItems;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = ReliquaryMod.MODID)
public final class BerserkerRage {

    private BerserkerRage() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        boolean hasVial = CuriosUtil.isEquipped(player, ModItems.BERSERKER_VIAL.get());
        boolean hasBrew = CuriosUtil.isEquipped(player, ModItems.BERSERKER_BREW.get());
        boolean hasChalice = CuriosUtil.isEquipped(player, ModItems.BERSERKER_CHALICE.get());


        if (player.getHealth() < 8.0f) {
            if (player.getHealth() < 6.0f && hasChalice) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 19, 2, true, false, true));
            }
            else if (hasBrew) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 19, 1, true, false, true));
            }
             else if (hasVial) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 19, 0, true, false, true));
            }
        }
    }
}
