package net.mojumo.productivecows.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mojumo.productivecows.block.ModBlocks;
import net.mojumo.productivecows.block.entity.MilkFilteringMachineBlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import net.minecraft.world.level.block.Block;

public class MilkFilteringMachineMenu extends AbstractContainerMenu {
    public final MilkFilteringMachineBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public MilkFilteringMachineMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public MilkFilteringMachineMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MILK_FILTERING_MACHINE_MENU.get(), id);
        checkContainerSize(inv, 1);
        blockEntity = (MilkFilteringMachineBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        IItemHandler itemHandler = blockEntity.getItemHandler();
        this.addSlot(new SlotItemHandler(itemHandler, 0, 80, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false; // UI restricted
            }
        });

        addDataSlots(data);
    }

    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }
    
    public FluidStack getFluid() {
        return blockEntity.getFluidHandler().getFluidInTank(0);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyStack = sourceStack.copy();

        if (index < 36) {
            // Player inventory to output slot (should not happen due to mayPlace)
            if (!moveItemStackTo(sourceStack, 36, 37, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Output slot to player inventory
            if (!moveItemStackTo(sourceStack, 0, 36, true)) {
                return ItemStack.EMPTY;
            }
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, (Block) ModBlocks.MILK_FILTERING_MACHINE_BLOCK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
