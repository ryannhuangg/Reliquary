package com.eboysaber.reliquary.item;

import com.eboysaber.reliquary.ReliquaryMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ReliquaryMod.MODID);

    public static DeferredItem<Item> registerAccessory(String name, Rarity rarity) {
        return ITEMS.register(name,
                () -> new AccessoryItem(
                        new Item.Properties().stacksTo(1).rarity(rarity), "tooltip." + ReliquaryMod.MODID + "." + name));
    }

    public static final DeferredItem<Item> CLOUD_IN_A_BOTTLE = registerAccessory("cloud_in_a_bottle", Rarity.UNCOMMON);
    public static final DeferredItem<Item> STORM_IN_A_BOTTLE = registerAccessory("storm_in_a_bottle", Rarity.RARE);
    public static final DeferredItem<Item> DUNERIDER_TALISMAN = registerAccessory("dunerider_talisman", Rarity.UNCOMMON);
    public static final DeferredItem<Item> BERSERKER_VIAL = registerAccessory("berserker_vial", Rarity.UNCOMMON);
    public static final DeferredItem<Item> BERSERKER_BREW = registerAccessory("berserker_brew", Rarity.RARE);
    public static final DeferredItem<Item> BERSERKER_CHALICE = registerAccessory("berserker_chalice", Rarity.EPIC);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
