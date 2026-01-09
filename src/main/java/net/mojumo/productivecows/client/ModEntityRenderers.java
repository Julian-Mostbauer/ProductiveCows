package net.mojumo.productivecows.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;
import net.mojumo.productivecows.ProductiveCows;
import net.mojumo.productivecows.entity.ModEntities;
import net.mojumo.productivecows.entity.ProductiveCowEntity;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@EventBusSubscriber(modid = ProductiveCows.MODID)
public class ModEntityRenderers {

    @SubscribeEvent
    public static void registerRenderers(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.PRODUCTIVE_COW.get(),
                manager -> new CowRenderer(manager) {
                    @Override
                    public ResourceLocation getTextureLocation(Cow entity) {
                        if (entity instanceof ProductiveCowEntity productiveCow && productiveCow.getCowType() != null) {
                            String texturePath = productiveCow.getCowType().texture();
                            if (texturePath != null && !texturePath.isEmpty()) {
                                return ResourceLocation.fromNamespaceAndPath(ProductiveCows.MODID, "textures/entity/" + texturePath + ".png");
                            }
                        }
                        // Fallback to default texture
                        return ResourceLocation.fromNamespaceAndPath(ProductiveCows.MODID, "textures/entity/default.png");
                    }
                });
    }
}
