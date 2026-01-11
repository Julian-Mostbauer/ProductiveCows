package net.mojumo.productivecows.fluid;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PCBucketItem extends BucketItem {
    public final int color;

    public PCBucketItem(PCFluid fluid, int color, Properties settings) {
        super(fluid, settings.stacksTo(1).craftRemainder(Items.BUCKET));
        this.color = ModFluids.Helpers.colMinLum(color);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack)).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)));
    }

}
