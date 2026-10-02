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
            () -> new AccessoryItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "tooltip.reliquary.cloud_in_a_bottle"));

    public static final DeferredItem<Item> STORM_IN_A_BOTTLE = ITEMS.register("storm_in_a_bottle",
            () -> new AccessoryItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), "tooltip.reliquary.storm_in_a_bottle"));

    public static final DeferredItem<Item> DUNERIDER_TALISMAN = ITEMS.register("dunerider_talisman",             
            () -> new AccessoryItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "tooltip.reliquary.dunerider_talisman"));

    public static final DeferredItem<Item> BERSERKER_VIAL = ITEMS.register("berserker_vial", 
            () -> new AccessoryItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "tooltip.reliquary.berserker_vial"));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
