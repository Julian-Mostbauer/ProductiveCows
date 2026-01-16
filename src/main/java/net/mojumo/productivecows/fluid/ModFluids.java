package net.mojumo.productivecows.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.mojumo.productivecows.ProductiveCows;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {

    private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, ProductiveCows.MODID);
    private static final DeferredRegister<Block> FLUID_BLOCKS = DeferredRegister.create(Registries.BLOCK, ProductiveCows.MODID);
    public static final DeferredRegister<Item> BUCKET_ITEMS = DeferredRegister.create(Registries.ITEM, ProductiveCows.MODID);
    private static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ProductiveCows.MODID);

    public static final DeferredHolder<FluidType, FluidType> FLAVORED_MILK_FLUID_TYPE = FLUID_TYPES.register("flavored_milk_fluid_type",
            FlavoredMilkFluidType::new);
    public static final DeferredHolder<Fluid, FlavoredMilkFluid> FLAVORED_MILK_FLUID_FLOWING = FLUIDS.register("flavored_milk_fluid_flowing",
            FlavoredMilkFluid.Flowing::new);
    public static final DeferredHolder<Fluid, FlavoredMilkFluid> FLAVORED_MILK_FLUID_SOURCE = FLUIDS.register("flavored_milk_fluid_source",
            FlavoredMilkFluid.Source::new);
    public static final DeferredHolder<Block, LiquidBlock> FLAVORED_MILK_FLUID_BLOCK = FLUID_BLOCKS.register("flavored_milk_fluid_block",
            FlavoredMilkFluidBlock::new);
    public static final DeferredHolder<Item, BucketItem> FLAVORED_MILK_FLUID_BUCKET = BUCKET_ITEMS.register("flavored_milk_fluid_bucket",
            () -> new BucketItem(FLAVORED_MILK_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


    public static void registerAll(IEventBus register) {
        FLUIDS.register(register);
        FLUID_BLOCKS.register(register);
        BUCKET_ITEMS.register(register);
        FLUID_TYPES.register(register);
    }
}
