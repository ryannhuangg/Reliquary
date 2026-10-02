package com.eboysaber.reliquary.util;

import com.eboysaber.reliquary.ReliquaryMod;
import com.eboysaber.reliquary.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import top.theillusivec4.curios.api.CuriosApi;
@EventBusSubscriber(modid = ReliquaryMod.MODID)
public final class BerserkerRage {

    private BerserkerRage() {
    }

    @SubscribeEvent
    public static void onPlayerTicket(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        boolean hasVial = CuriosApi.getCuriosInventory(player)
            .map(inventory -> !inventory.findCurios(ModItems.BERSERKER_VIAL.get()).isEmpty())
            .orElse(false);

        if (hasVial && player.getHealth() < 8.0f) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 0, true, false, true));
            
        }
    }
}
