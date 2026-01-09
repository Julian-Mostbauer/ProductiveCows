package net.mojumo.productivecows.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CowSpawnerBlock extends Block {
    public CowSpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getJumpFactor() {
        return 10f;
    }
}
