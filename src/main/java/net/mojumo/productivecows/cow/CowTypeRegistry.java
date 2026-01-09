package net.mojumo.productivecows.cow;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CowTypeRegistry {
    private static final Map<ResourceLocation, CowType> TYPES = new HashMap<>();

    public static void loadFromJson(ResourceManager resourceManager) throws IOException {
        // List all JSON files in the data/productivecows/cow_types directory
        var resources = resourceManager.listResources("cow_types", path -> path.getPath().endsWith(".json"));
        for (var entry : resources.entrySet()) {
            try (var reader = new InputStreamReader(resourceManager.getResource(entry.getKey()).get().open())) {
                JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
                ResourceLocation id = ResourceLocation.parse(obj.get("id").getAsString());
                ResourceLocation material = ResourceLocation.parse(obj.get("material").getAsString());
                String texture = obj.get("texture").getAsString();
                TYPES.put(id, new CowType(id, material, texture));
            }
        }
    }

    public static CowType get(ResourceLocation id) {
        return TYPES.get(id);
    }

    public static Collection<CowType> all() {
        return TYPES.values();
    }

    public static int size() {
        return TYPES.size();
    }

    public static CowType getRandom(RandomSource random) {
        int index = random.nextInt(TYPES.size());
        return TYPES.values().stream().skip(index).findFirst().orElse(null);
    }
}
