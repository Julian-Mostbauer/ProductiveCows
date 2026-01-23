package net.mojumo.productivecows.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.mojumo.productivecows.ProductiveCows;
import net.mojumo.productivecows.fluid.flavoredmilk.FlavoredMilkFluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class FluidClientExtension {
    public static void registerFluidClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(
                new IClientFluidTypeExtensions() {
                    @Override
                    public ResourceLocation getStillTexture() {
                        return ResourceLocation.fromNamespaceAndPath(
                                ProductiveCows.MODID,
                                "block/flavored_milk_fluid_still"
                        );
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return ResourceLocation.fromNamespaceAndPath(
                                ProductiveCows.MODID,
                                "block/flavored_milk_fluid_flow"
                        );
                    }

                    @Override
                    public int getTintColor() {
                        return 0xFFFFFFFF;
                    }

                    @Override
                    public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                        return state.hasProperty(FlavoredMilkFluid.COLOR_RED) && state.hasProperty(FlavoredMilkFluid.COLOR_GREEN) && state.hasProperty(FlavoredMilkFluid.COLOR_BLUE)
                                ? FlavoredMilkFluid.getColor(state.getValue(FlavoredMilkFluid.COLOR_RED), state.getValue(FlavoredMilkFluid.COLOR_GREEN), state.getValue(FlavoredMilkFluid.COLOR_BLUE))
                                : this.getTintColor();
                    }
                },
                ModFluids.FLAVORED_MILK_FLUID_TYPE.get()
        );
    }
}
