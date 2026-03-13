package net.mojumo.productivecows.cow;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record CowType(ResourceLocation id, ResourceLocation material, String texture, List<ResourceLocation> parents) {
    public CowType(ResourceLocation id, ResourceLocation material, String texture) {
        this(id, material, texture, List.of());
    }
}

