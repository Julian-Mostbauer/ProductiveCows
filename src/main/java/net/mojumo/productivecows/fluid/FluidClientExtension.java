package net.mojumo.productivecows.fluid;

import net.minecraft.resources.ResourceLocation;
import net.mojumo.productivecows.ProductiveCows;
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

                },
                ModFluids.FLAVORED_MILK_FLUID_TYPE.get()
        );
    }
}
