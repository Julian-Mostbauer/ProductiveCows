package net.mojumo.productivecows.fluid.flavoredmilk;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.mojumo.productivecows.fluid.ModFluids;

import java.util.Random;

public class FlavoredMilkFluidBlock extends LiquidBlock {
    public FlavoredMilkFluidBlock() {
        super(ModFluids.FLAVORED_MILK_FLUID_SOURCE.get(), Properties.of()
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .replaceable()
                .noCollission()
                .strength(100.0F)
                .pushReaction(PushReaction.DESTROY)
                .noLootTable()
                .liquid()
                .sound(SoundType.EMPTY)
        );
    }
    private static Random random = new Random();
    @Override
    public FluidState getFluidState(BlockState pState) {
        return super.getFluidState(pState)
                .setValue(FlavoredMilkFluid.COLOR_RED, random.nextInt(0, 8))
                .setValue(FlavoredMilkFluid.COLOR_GREEN, random.nextInt(0, 8))
                .setValue(FlavoredMilkFluid.COLOR_BLUE, random.nextInt(0, 8));
    }
}
