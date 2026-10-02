package com.eboysaber.reliquary.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public final class CuriosUtil {

    private CuriosUtil() {
    }

    public static boolean isEquipped(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player)
                .map(inventory -> !inventory.findCurios(item).isEmpty())
                .orElse(false);
    }
}
