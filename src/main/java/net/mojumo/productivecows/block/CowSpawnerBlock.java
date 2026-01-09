package net.mojumo.productivecows.block;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.mojumo.productivecows.ProductiveCows;
import net.mojumo.productivecows.cow.CowType;
import net.mojumo.productivecows.cow.CowTypeRegistry;
import net.mojumo.productivecows.entity.ModEntities;
import net.mojumo.productivecows.entity.ProductiveCowEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CowSpawnerBlock extends Block {
    public CowSpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, @Nullable net.minecraft.world.entity.player.Player player, BlockHitResult b) {
        level.playSound(player, pos, SoundEvents.COW_MILK, SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.FAIL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player == null) return ItemInteractionResult.FAIL;

        if (interactionHand == InteractionHand.OFF_HAND && player.getMainHandItem().isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        ProductiveCows.LOGGER.info("Itemstack: item:{}, isempty:{}", itemStack.getItem(), itemStack.isEmpty());

        if (!player.isCreative()) {
            player.displayClientMessage(Component.literal("You can only use this block in creative mode"), true);
            return ItemInteractionResult.FAIL;
        }

        if (itemStack.getItem().equals(Items.AIR)) {
            player.displayClientMessage(Component.literal("Try using a supported item on this block"), true);
            return ItemInteractionResult.FAIL;
        }

        if (!level.isClientSide) {
            ProductiveCowEntity cow = new ProductiveCowEntity(ModEntities.PRODUCTIVE_COW.get(), level);
            cow.setPos(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);

            var item = itemStack.getItem();
            CowType type = CowTypeRegistry.getCowForItem(item);
            if (type != null) {
                cow.setCowType(type);
                level.addFreshEntity(cow);
                return ItemInteractionResult.SUCCESS;
            } else {
                ProductiveCows.LOGGER.info("{} has not cow that supports it", item);
                player.displayClientMessage(Component.literal(item.toString() + " has not cow that supports it"), true);
                return ItemInteractionResult.FAIL;
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
