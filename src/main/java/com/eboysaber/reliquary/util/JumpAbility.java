package com.eboysaber.reliquary.util;

import com.eboysaber.reliquary.item.ModItems;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;

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
}
