package net.mojumo.productivecows.cow;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CowTypeRegistry {
    private static final Map<ResourceLocation, CowType> TYPES = new HashMap<>();

    public static void register(CowType type) {
        TYPES.put(type.id(), type);
    }

    public static CowType get(ResourceLocation id) {
        return TYPES.get(id);
    }

    public static Collection<CowType> all() {
        return TYPES.values();
    }
}

