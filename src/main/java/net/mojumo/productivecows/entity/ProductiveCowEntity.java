package net.mojumo.productivecows.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.mojumo.productivecows.ProductiveCows;
import net.mojumo.productivecows.cow.CowType;
import net.mojumo.productivecows.cow.CowTypeRegistry;
import javax.annotation.Nullable;


public class ProductiveCowEntity extends Cow {

    private static final EntityDataAccessor<String> COW_TYPE =
            SynchedEntityData.defineId(ProductiveCowEntity.class, EntityDataSerializers.STRING);

    public ProductiveCowEntity(EntityType<? extends Cow> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Cow.createAttributes();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COW_TYPE, "productivecows:default");
    }

    public CowType getCowType() {
        return CowTypeRegistry.get(ResourceLocation.parse(entityData.get(COW_TYPE)));
    }

    public void setCowType(CowType type) {
        entityData.set(COW_TYPE, type.id().toString());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("CowType", entityData.get(COW_TYPE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("CowType")) {
            entityData.set(COW_TYPE, tag.getString("CowType"));
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor level,
            DifficultyInstance difficulty,
            MobSpawnType reason,
            @Nullable SpawnGroupData spawnData
    ) {
        setCowType(CowTypeRegistry.all().iterator().next());
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (itemstack.is(Items.BUCKET) && !this.isBaby()) {
            CowType type = getCowType();
            ResourceLocation material = type.material();
            Item materialItem = BuiltInRegistries.ITEM.get(material);
            if (materialItem != null) {
                player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                ItemStack result = ItemUtils.createFilledResult(itemstack, player, new ItemStack(materialItem));
                player.setItemInHand(interactionHand, result);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        return super.mobInteract(player, interactionHand);
    }

}

