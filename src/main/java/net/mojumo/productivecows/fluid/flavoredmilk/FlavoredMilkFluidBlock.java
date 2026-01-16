package net.mojumo.productivecows.fluid.flavoredmilk;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.mojumo.productivecows.fluid.ModFluids;

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
}
