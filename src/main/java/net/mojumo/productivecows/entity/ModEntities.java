package net.mojumo.productivecows.entity;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.Level;
import net.mojumo.productivecows.ProductiveCows;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ProductiveCows.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<ProductiveCowEntity>> PRODUCTIVE_COW =
            ENTITIES.register("productive_cow",
                    () -> EntityType.Builder
                            .of(ProductiveCowEntity::new, MobCategory.CREATURE)
                            .sized(0.9f, 1.4f)
                            .build(ProductiveCows.MODID + ":productive_cow"));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
