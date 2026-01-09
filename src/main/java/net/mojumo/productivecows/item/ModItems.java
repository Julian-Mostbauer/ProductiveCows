package net.mojumo.productivecows.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.mojumo.productivecows.ProductiveCows;
import net.mojumo.productivecows.entity.ModEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, ProductiveCows.MODID);

    public static final DeferredHolder<Item, SpawnEggItem> PRODUCTIVE_COW_EGG =
            ITEMS.register("productive_cow_spawn_egg", () ->
                    new SpawnEggItem(
                            ModEntities.PRODUCTIVE_COW.get(),
                            0xAAAAAA,  // base color
                            0x555555,  // spots color
                            new Item.Properties()
                    )
            );


    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
