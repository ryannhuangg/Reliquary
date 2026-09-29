package com.eboysaber.reliquary.item;

import com.eboysaber.reliquary.ReliquaryMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ReliquaryMod.MODID);

    public static final DeferredItem<Item> CLOUD_IN_A_BOTTLE = ITEMS.register("cloud_in_a_bottle",
            () -> new Item(new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON)
            ));

    public static final DeferredItem<Item> STORM_IN_A_BOTTLE = ITEMS.register("storm_in_a_bottle",
            () -> new Item(new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
