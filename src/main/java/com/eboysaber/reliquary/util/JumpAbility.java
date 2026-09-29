package com.eboysaber.reliquary.util;

import com.eboysaber.reliquary.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = "reliquary")
public final class JumpAbility {

    private JumpAbility() {
    }

    public static int count(Player player) {
        return CuriosApi.getCuriosInventory(player).map(inventory -> {
            if (!inventory.findCurios(ModItems.STORM_IN_A_BOTTLE.get()).isEmpty()) {
                return 2;
            }
            if (!inventory.findCurios(ModItems.CLOUD_IN_A_BOTTLE.get()).isEmpty()) {
                return inventory.findCurios(ModItems.CLOUD_IN_A_BOTTLE.get()).size();
            }
            return 0; 
        }).orElse(0); 
    }

    @SubscribeEvent
    public static void onPlayerFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player && count(player)>0) {
            event.setDistance(0.0f);
        }
    }
}
