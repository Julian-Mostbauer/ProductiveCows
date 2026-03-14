package net.mojumo.productivecows.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.mojumo.productivecows.ProductiveCows;
import net.neoforged.neoforge.fluids.FluidStack;

public class MilkFilteringMachineScreen extends AbstractContainerScreen<MilkFilteringMachineMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ProductiveCows.MODID, "textures/gui/milk_filtering_machine.png");

    public MilkFilteringMachineScreen(MilkFilteringMachineMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderFluidTank(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.getProgress() > 0) {
            guiGraphics.blit(TEXTURE, x + 105, y + 33, 176, 0, menu.getProgress() * 24 / menu.getMaxProgress(), 17);
        }
    }

    private void renderFluidTank(GuiGraphics guiGraphics, int x, int y) {
        FluidStack fluidStack = menu.getFluid();
        if (!fluidStack.isEmpty()) {
            int amount = fluidStack.getAmount();
            int maxHeight = 50;
            int height = (int) (maxHeight * ((float) amount / 8000));
            // Placeholder: In a real mod we would use IClientFluidTypeExtensions to get the texture and color
            guiGraphics.fill(x + 26, y + 20 + (maxHeight - height), x + 26 + 16, y + 20 + maxHeight, 0xFFFFFFFF);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
