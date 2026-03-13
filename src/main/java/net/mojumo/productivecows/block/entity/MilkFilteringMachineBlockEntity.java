package net.mojumo.productivecows.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MilkFilteringMachineBlockEntity extends BlockEntity {
    private boolean isMaster = false;
    private BlockPos masterPos = null;

    public MilkFilteringMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MILK_FILTERING_MACHINE_BE.get(), pos, state);
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

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("IsMaster", isMaster);
        if (masterPos != null) {
            tag.putLong("MasterPos", masterPos.asLong());
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        isMaster = tag.getBoolean("IsMaster");
        if (tag.contains("MasterPos")) {
            masterPos = BlockPos.of(tag.getLong("MasterPos"));
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MilkFilteringMachineBlockEntity blockEntity) {
        if (level.isClientSide) return;
        
        if (blockEntity.isMaster) {
            // Processing logic will go here
        }
    }
}
