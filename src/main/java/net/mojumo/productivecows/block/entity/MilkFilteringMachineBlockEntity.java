package net.mojumo.productivecows.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.mojumo.productivecows.screen.MilkFilteringMachineMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.mojumo.productivecows.cow.CowType;
import net.mojumo.productivecows.cow.CowTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.mojumo.productivecows.fluid.ModFluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

public class MilkFilteringMachineBlockEntity extends BlockEntity implements MenuProvider {
    private boolean isMaster = false;
    private BlockPos masterPos = null;
    private int progress = 0;
    private static final int MAX_PROGRESS = 100;

    protected final ContainerData data;

    private final FluidTank fluidTank = new FluidTank(8000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.FLAVORED_MILK_FLUID_SOURCE.get();
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return false; // Prevent putting items in manually
        }
    };

    public MilkFilteringMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MILK_FILTERING_MACHINE_BE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MilkFilteringMachineBlockEntity.this.progress;
                    case 1 -> MilkFilteringMachineBlockEntity.MAX_PROGRESS;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MilkFilteringMachineBlockEntity.this.progress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.productivecows.milk_filtering_machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MilkFilteringMachineMenu(id, inventory, this, this.data);
    }

    public void setMaster(BlockPos masterPos) {
        this.masterPos = masterPos;
        this.isMaster = (masterPos != null && masterPos.equals(this.worldPosition));
        setChanged();
    }

    public boolean isMaster() {
        return isMaster;
    }

    public BlockPos getMasterPos() {
        return masterPos;
    }

    public IFluidHandler getFluidHandler() {
        return fluidTank;
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("IsMaster", isMaster);
        if (masterPos != null) {
            tag.putLong("MasterPos", masterPos.asLong());
        }
        if (isMaster) {
            tag.put("FluidTank", fluidTank.writeToNBT(registries, new CompoundTag()));
            tag.put("Inventory", itemHandler.serializeNBT(registries));
            tag.putInt("Progress", progress);
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        isMaster = tag.getBoolean("IsMaster");
        if (tag.contains("MasterPos")) {
            masterPos = BlockPos.of(tag.getLong("MasterPos"));
        }
        if (tag.contains("FluidTank")) {
            fluidTank.readFromNBT(registries, tag.getCompound("FluidTank"));
        }
        if (tag.contains("Inventory")) {
            itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
        progress = tag.getInt("Progress");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MilkFilteringMachineBlockEntity blockEntity) {
        if (level.isClientSide) return;

        if (blockEntity.isMaster) {
            FluidStack fluid = blockEntity.fluidTank.getFluid();
            if (!fluid.isEmpty() && fluid.getAmount() >= 100) {
                // Get cow type from fluid components
                CustomData customData = fluid.get(DataComponents.CUSTOM_DATA);
                String cowTypeId = customData != null ? customData.copyTag().getString("cow_type") : "";
                if (!cowTypeId.isEmpty()) {
                    CowType cowType = CowTypeRegistry.get(ResourceLocation.parse(cowTypeId));
                    if (cowType != null) {
                        blockEntity.progress++;
                        if (blockEntity.progress >= MAX_PROGRESS) {
                            ItemStack result = new ItemStack(BuiltInRegistries.ITEM.get(cowType.material()));
                            if (blockEntity.canOutput(result)) {
                                blockEntity.fluidTank.drain(100, IFluidHandler.FluidAction.EXECUTE);
                                blockEntity.outputItem(result);
                                blockEntity.progress = 0;
                            } else {
                                blockEntity.progress = MAX_PROGRESS; // Stay at max progress if blocked
                            }
                        }
                        blockEntity.setChanged();
                    }
                } else {
                    blockEntity.progress = 0;
                }
            } else {
                blockEntity.progress = 0;
            }
        }
    }

    private boolean canOutput(ItemStack stack) {
        ItemStack existing = itemHandler.getStackInSlot(0);
        if (existing.isEmpty()) return true;
        return ItemStack.isSameItemSameComponents(existing, stack) && (existing.getCount() + stack.getCount() <= existing.getMaxStackSize());
    }

    private void outputItem(ItemStack stack) {
        ItemStack existing = itemHandler.getStackInSlot(0);
        if (existing.isEmpty()) {
            itemHandler.setStackInSlot(0, stack.copy());
        } else {
            existing.grow(stack.getCount());
        }
    }
}
