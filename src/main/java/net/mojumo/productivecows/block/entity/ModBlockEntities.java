package net.mojumo.productivecows.block.entity;

import net.mojumo.productivecows.ProductiveCows;
import net.mojumo.productivecows.block.ModBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ProductiveCows.MODID);

    public static final Supplier<BlockEntityType<MilkFilteringMachineBlockEntity>> MILK_FILTERING_MACHINE_BE =
            BLOCK_ENTITIES.register("milk_filtering_machine_be",
                    () -> BlockEntityType.Builder.of(MilkFilteringMachineBlockEntity::new,
                            ModBlocks.MILK_FILTERING_MACHINE_BLOCK.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
