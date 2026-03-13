package net.mojumo.productivecows.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.mojumo.productivecows.ProductiveCows;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mojumo.productivecows.block.entity.MilkFilteringMachineBlockEntity;
import net.mojumo.productivecows.block.entity.ModBlockEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class MilkFilteringMachineBlock extends Block implements EntityBlock {
    public MilkFilteringMachineBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof MilkFilteringMachineBlockEntity machineBe) {
            BlockPos masterPos = machineBe.getMasterPos();
            if (masterPos != null) {
                BlockEntity masterBe = level.getBlockEntity(masterPos);
                if (masterBe instanceof MilkFilteringMachineBlockEntity master) {
                    // Try to interact with bucket
                    if (FluidUtil.interactWithFluidHandler(player, InteractionHand.MAIN_HAND, master.getFluidHandler())) {
                        return InteractionResult.SUCCESS;
                    }

                    // Open UI
                    player.openMenu(master, masterPos);
                    return InteractionResult.SUCCESS;
                }
            } else {
                player.displayClientMessage(Component.literal("Not part of a multiblock."), true);
            }
        }
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.MILK_FILTERING_MACHINE_BE.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.MILK_FILTERING_MACHINE_BE.get() 
                ? (lvl, pos, st, be) -> MilkFilteringMachineBlockEntity.tick(lvl, pos, st, (MilkFilteringMachineBlockEntity) be)
                : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide) return;
        
        // Attempt to form a 2x2x2 multiblock
        // For simplicity, we check if this is the bottom-front-left corner and others are present
        // In a real implementation, we might want more complex detection
        checkAndFormMultiblock(level, pos);
    }

    private void checkAndFormMultiblock(Level level, BlockPos pos) {
        // Attempt to find the "bottom-front-left" corner of a potential 2x2x2 cube
        // We check all 8 possible 2x2x2 cubes that could contain the newly placed block at 'pos'
        for (int dx = -1; dx <= 0; dx++) {
            for (int dy = -1; dy <= 0; dy++) {
                for (int dz = -1; dz <= 0; dz++) {
                    BlockPos origin = pos.offset(dx, dy, dz);
                    if (isFullCube(level, origin)) {
                        formCube(level, origin);
                        return; // Found and formed one, we're done
                    }
                }
            }
        }
    }

    private boolean isFullCube(Level level, BlockPos origin) {
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    if (!level.getBlockState(origin.offset(x, y, z)).is(this)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void formCube(Level level, BlockPos origin) {
        ProductiveCows.LOGGER.info("Forming multiblock at {}", origin);
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    BlockPos p = origin.offset(x, y, z);
                    BlockEntity be = level.getBlockEntity(p);
                    if (be instanceof MilkFilteringMachineBlockEntity machineBe) {
                        machineBe.setMaster(origin);
                    }
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof MilkFilteringMachineBlockEntity machineBe) {
                BlockPos masterPos = machineBe.getMasterPos();
                if (masterPos != null) {
                    breakMultiblock(level, masterPos);
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    private void breakMultiblock(Level level, BlockPos masterPos) {
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    BlockPos p = masterPos.offset(x, y, z);
                    BlockEntity be = level.getBlockEntity(p);
                    if (be instanceof MilkFilteringMachineBlockEntity machineBe) {
                        machineBe.setMaster(null);
                    }
                }
            }
        }
    }
}
