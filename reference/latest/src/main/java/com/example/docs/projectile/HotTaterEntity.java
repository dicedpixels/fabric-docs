package com.example.docs.projectile;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

// #region entity
public class HotTaterEntity extends ThrowableItemProjectile {
	// #region constructors
	public HotTaterEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
		super(type, level);
	}

	public HotTaterEntity(Level level, LivingEntity owner, ItemStack itemStack) {
		super(ExampleModProjectile.HOT_TATER_ENTITY_TYPE, owner, level, itemStack);
	}

	public HotTaterEntity(Level level, double x, double y, double z, ItemStack itemStack) {
		super(ExampleModProjectile.HOT_TATER_ENTITY_TYPE, x, y, z, level, itemStack);
	}
	// #endregion constructors

	// #region default_item
	@Override
	protected @NonNull Item getDefaultItem() {
		return ExampleModProjectile.HOT_TATER_ITEM;
	}
	// #endregion default_item

	// #region on_hit
	@Override
	protected void onHit(@NonNull HitResult hitResult) {
		super.onHit(hitResult);
		Level level = level();

		// Only modify the world on the server.
		if (!level.isClientSide()) {
			// If the projectile hits a block, place fire on the face it hit.
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				BlockHitResult blockHitResult = (BlockHitResult) hitResult;
				BlockPos pos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());

				if (level.isEmptyBlock(pos)) {
					level.setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
				}
			}

			// Remove the projectile after any hit, or it sinks into the ground forever.
			discard();
		}
	}
	// #endregion on_hit

	// #region on_hit_entity
	@Override
	protected void onHitEntity(@NonNull EntityHitResult hitResult) {
		super.onHitEntity(hitResult);

		// Only modify the world on the server.
		if (!level().isClientSide()) {
			hitResult.getEntity().igniteForSeconds(5);
		}
	}
	// #endregion on_hit_entity
}
// #endregion entity
