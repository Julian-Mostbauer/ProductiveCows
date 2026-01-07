package net.mojumo.productivecows;

import net.mojumo.productivecows.entity.ModEntities;
import net.mojumo.productivecows.entity.ProductiveCowEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ProductiveCows.MODID)
public class ModEntityAttributes {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(
                ModEntities.PRODUCTIVE_COW.get(),
                ProductiveCowEntity.createAttributes().build()
        );
    }
}