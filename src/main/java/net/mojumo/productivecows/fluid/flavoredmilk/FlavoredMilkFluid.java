package net.mojumo.productivecows.fluid.flavoredmilk;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.mojumo.productivecows.fluid.ModFluids;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import java.util.Random;

public abstract class FlavoredMilkFluid extends BaseFlowingFluid {
    public static final Properties PROPERTIES = new Properties(
            ModFluids.FLAVORED_MILK_FLUID_TYPE,
            ModFluids.FLAVORED_MILK_FLUID_FLOWING,
            ModFluids.FLAVORED_MILK_FLUID_SOURCE
    ).bucket(ModFluids.FLAVORED_MILK_FLUID_BUCKET).block(ModFluids.FLAVORED_MILK_FLUID_BLOCK);

//     public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 16777215);

    protected FlavoredMilkFluid(Properties properties) {
        super(properties);
    }

    public static int getRandomColor() {
        return new Random().nextInt(16777216);
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
            //pBuilder.add(COLOR);
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
            //pBuilder.add(COLOR);
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
