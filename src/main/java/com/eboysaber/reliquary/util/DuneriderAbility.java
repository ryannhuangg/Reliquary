package com.eboysaber.reliquary.util;

import com.eboysaber.reliquary.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = "reliquary")
public final class DuneriderAbility {

    private static final TagKey<Block> DUNE_TERRAIN =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("reliquary", "dunerider_terrain"));

    private static boolean hasTalisman(Player player) {
        return CuriosApi.getCuriosInventory(player)
            .map(inventory -> !inventory.findCurios(ModItems.DUNERIDER_TALISMAN.get()).isEmpty())
            .orElse(false);
    }

    private static boolean onDesert(Player player) {
        return (player.level().getBlockState(player.blockPosition().below())).is(DUNE_TERRAIN);
    }

    private DuneriderAbility() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        if (hasTalisman(player)) {
            if (onDesert(player)) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12, 0, true, false, true));
            }
        }
    } 
}

