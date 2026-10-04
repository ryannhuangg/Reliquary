package com.eboysaber.reliquary.item;

import com.eboysaber.reliquary.ReliquaryMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ReliquaryMod.MODID);

    public static final Supplier<CreativeModeTab> AMULETS_ITEMS_TAB = CREATIVE_MODE_TAB.register("amulets_item_tab",
           () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.EFFIGY_OF_CONDEMNED.get()))
                .title(Component.translatable("creativetab.reliquary.amulets"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModItems.EFFIGY_OF_CONDEMNED.get());
                })
                .build());

    public static final Supplier<CreativeModeTab> RELICS_ITEMS_TAB = CREATIVE_MODE_TAB.register("relics_item_tab",
           () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CLOUD_IN_A_BOTTLE.get()))
                .title(Component.translatable("creativetab.reliquary.relics"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModItems.CLOUD_IN_A_BOTTLE.get());
                    output.accept(ModItems.STORM_IN_A_BOTTLE.get());
                    output.accept(ModItems.DUNERIDER_TALISMAN.get());
                    output.accept(ModItems.BERSERKER_VIAL.get());
                    output.accept(ModItems.BERSERKER_BREW.get());
                    output.accept(ModItems.BERSERKER_CHALICE.get());
                })
                .withTabsBefore(ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(ReliquaryMod.MODID, "amulets_item_tab")))
                .build());

    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
