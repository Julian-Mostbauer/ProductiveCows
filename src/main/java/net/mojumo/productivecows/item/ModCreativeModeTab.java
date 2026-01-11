package net.mojumo.productivecows.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.mojumo.productivecows.ProductiveCows;
import net.mojumo.productivecows.fluid.ModFluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ProductiveCows.MODID);

    public static final Supplier<CreativeModeTab> PRODUCTIVE_COW_MOD_TAB =
            CREATIVE_MODE_TAB.register("productive_cows_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.PRODUCTIVE_COW_EGG.get()))
                    .title(Component.translatable("creativetab.productive_cows.productive_cows"))
                    .displayItems(((itemDisplayParameters, output) ->
                    {
                        for (var item : ModItems.ITEMS.getEntries()){
                            output.accept(item.get());
                        }
                        for (var item: ModFluids.BUCKET_ITEMS.getEntries()){
                            output.accept(item.get());
                        }
                    }))
                    .build());

    public static void register(IEventBus bus){
        CREATIVE_MODE_TAB.register(bus);
    }
}
