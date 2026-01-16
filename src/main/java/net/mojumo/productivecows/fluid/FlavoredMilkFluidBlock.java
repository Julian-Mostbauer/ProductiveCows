package net.mojumo.productivecows.fluid;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class FlavoredMilkFluidBlock extends LiquidBlock {
    public FlavoredMilkFluidBlock() {
        super(ModFluids.FLAVORED_MILK_FLUID_SOURCE.get(), Properties.of()
                .mapColor(MapColor.COLOR_LIGHT_GREEN)
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
