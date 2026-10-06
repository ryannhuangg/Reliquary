package com.eboysaber.reliquary.item;

import com.eboysaber.reliquary.ReliquaryMod;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ReliquaryMod.MODID);


    public static DeferredItem<Item> registerAccessory(String name) {
        return ITEMS.register(name,
                () -> new AccessoryItem(
                        new Item.Properties().stacksTo(1), "tooltip." + ReliquaryMod.MODID + "." + name));
    }

    public static final DeferredItem<Item> CLOUD_IN_A_BOTTLE = registerAccessory("cloud_in_a_bottle"); // UNCOMMON
    public static final DeferredItem<Item> STORM_IN_A_BOTTLE = registerAccessory("storm_in_a_bottle"); // EPIC
    public static final DeferredItem<Item> DUNERIDER_TALISMAN = registerAccessory("dunerider_talisman"); //UNCOMMON
    public static final DeferredItem<Item> BERSERKER_VIAL = registerAccessory("berserker_vial"); // RARE
    public static final DeferredItem<Item> BERSERKER_BREW = registerAccessory("berserker_brew"); // EPIC
    public static final DeferredItem<Item> BERSERKER_CHALICE = registerAccessory("berserker_chalice"); // LEGENDARY
    public static final DeferredItem<Item> EFFIGY_OF_CONDEMNED = registerAccessory("effigy_of_condemned"); //DIVINE
    public static final DeferredItem<Item> VANGUARD_BRACE = registerAccessory("vanguard_brace"); // EPIC
    public static final DeferredItem<Item> VANGUARD_SHIELD = registerAccessory("vanguard_shield"); // LEGENDARY

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
