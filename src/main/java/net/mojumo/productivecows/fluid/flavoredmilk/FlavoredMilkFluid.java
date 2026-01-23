package net.mojumo.productivecows.fluid.flavoredmilk;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.mojumo.productivecows.fluid.ModFluids;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class FlavoredMilkFluid extends BaseFlowingFluid {
    public static final Properties PROPERTIES = new Properties(
            ModFluids.FLAVORED_MILK_FLUID_TYPE,
            ModFluids.FLAVORED_MILK_FLUID_FLOWING,
            ModFluids.FLAVORED_MILK_FLUID_SOURCE
    ).bucket(ModFluids.FLAVORED_MILK_FLUID_BUCKET).block(ModFluids.FLAVORED_MILK_FLUID_BLOCK);

    public static final IntegerProperty COLOR_RED = IntegerProperty.create("color_red", 0, 8);
    public static final IntegerProperty COLOR_GREEN = IntegerProperty.create("color_green", 0, 8);
    public static final IntegerProperty COLOR_BLUE = IntegerProperty.create("color_blue", 0, 8);
    public static final List<IntegerProperty> COLOR_PROPERTIES = List.of(COLOR_RED, COLOR_GREEN, COLOR_BLUE);

    protected FlavoredMilkFluid(Properties properties) {
        super(properties);
    }

    public static int getColor(int r, int g, int b) {
        return (32 * r) << 16 | (32 * g) << 8 | (32 * b);
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLAVORED_MILK_FLUID_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.FLAVORED_MILK_FLUID_SOURCE.get();
    }

    @Override
    public Item getBucket() {
        return ModFluids.FLAVORED_MILK_FLUID_BUCKET.get();
    }

    @Override
    protected boolean canConvertToSource(Level pLevel) {
        return false;
    }

    public static class Flowing extends FlavoredMilkFluid {
        public Flowing() {
            super(PROPERTIES);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            pBuilder.add(LEVEL);
            COLOR_PROPERTIES.forEach(pBuilder::add);
        }

        @Override
        public int getAmount(FluidState pState) {
            return pState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState pState) {
            return false;
        }
    }

    public static class Source extends FlavoredMilkFluid {
        public Source() {
            super(PROPERTIES);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            COLOR_PROPERTIES.forEach(pBuilder::add);
        }

        @Override
        public int getAmount(FluidState pState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState pState) {
            return true;
        }
    }
}
