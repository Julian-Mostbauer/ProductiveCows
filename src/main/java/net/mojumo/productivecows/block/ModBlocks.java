package net.mojumo.productivecows.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.mojumo.productivecows.ProductiveCows;
import net.mojumo.productivecows.entity.ProductiveCowEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.mojumo.productivecows.ProductiveCows.ITEMS;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCK =
            DeferredRegister.create(Registries.BLOCK, ProductiveCows.MODID);

    public static final DeferredHolder<Block, CowSpawnerBlock> COW_SPAWNER_BLOCK =
            BLOCK.register("cow_spawner_block", () ->
                    new CowSpawnerBlock(Block.Properties.of()));
    public static final DeferredItem<BlockItem> COW_SPAWNER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", COW_SPAWNER_BLOCK);
    public static void register(IEventBus bus) {
        BLOCK.register(bus);
    }
}
