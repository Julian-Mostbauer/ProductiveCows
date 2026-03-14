package net.mojumo.productivecows.screen;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.mojumo.productivecows.ProductiveCows;
import net.neoforged.bus.api.IEventBus;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ProductiveCows.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<MilkFilteringMachineMenu>> MILK_FILTERING_MACHINE_MENU =
            MENUS.register("milk_filtering_machine_menu", () -> IMenuTypeExtension.create(MilkFilteringMachineMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
