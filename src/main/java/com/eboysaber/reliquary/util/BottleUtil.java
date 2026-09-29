package com.eboysaber.reliquary.util;

import com.eboysaber.reliquary.item.ModItems;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;

public final class BottleUtil {

    private BottleUtil() {
    }

    public static int count(Player player){
        return CuriosApi.getCuriosInventory(player)
            .map(inventory -> inventory.findCurios(ModItems.CLOUD_IN_A_BOTTLE.get()).size())
            .orElse(0);
    }
}
