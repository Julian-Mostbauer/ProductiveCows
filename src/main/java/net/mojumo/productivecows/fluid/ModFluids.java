package net.mojumo.productivecows.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

    private static final FluidRegisterer PC_MILK = new FluidRegisterer("pc_milk", 0xFFFFFF);

    public static void registerAll(IEventBus register) {
        FLUIDS.register(register);
        FLUID_BLOCKS.register(register);
        BUCKET_ITEMS.register(register);
        FLUID_TYPES.register(register);
    }

    private static class FluidRegisterer {
        private final DeferredHolder<Fluid, PCFluid> fluid;
        private DeferredHolder<Block, PCFluidBlock> fluidBlock;
        private DeferredHolder<Item, PCBucketItem> bucketItem;
        private DeferredHolder<FluidType, PCFluidType> fluidType;

        public FluidRegisterer(String name, int color) {
            fluidBlock = FLUID_BLOCKS.register(name + "_block", () -> new PCFluidBlock(color));

            fluidType = FLUID_TYPES.register(name + "_fluid_type", () -> new PCFluidType(
                            () -> fluidBlock.get(),
                            FluidType.Properties.create().canSwim(true).viscosity(0).canDrown(true).canConvertToSource(true)
                    )
            );

            fluid = FLUIDS.register(name + "_bucket", () ->
                    new PCFluid(
                            () -> fluidBlock.get(),
                            () -> bucketItem.get(),
                            () -> fluidType.get(),
                            color
                    )
            );

            bucketItem =
                    BUCKET_ITEMS.register(name + "_bucket", () ->
                            new PCBucketItem(
                                    fluid.get(),
                                    color,
                                    new Item.Properties().stacksTo(1).craftRemainder(net.minecraft.world.item.Items.BUCKET)
                            )
                    );
        }
    }

    public static class Helpers {
        public static int colMinLum(int color) {
            int r = color & 0xFF, g = (color >> 8) & 0xFF, b = (color >> 16) & 0xFF;
            double lum = (0.2126 * r + 0.7152 * g + 0.0722 * b) / 255d;
            if (lum >= 0.3) return color;
            if (lum == 0) return 0x4C4C4C;
            double s = 0.3 / lum;
            return (Math.min((int) (r * s), 255))
                    | (Math.min((int) (g * s), 255) << 8)
                    | (Math.min((int) (b * s), 255) << 16);
        }
    }
}
