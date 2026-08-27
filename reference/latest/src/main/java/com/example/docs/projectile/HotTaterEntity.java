package com.example.docs.projectile;

import org.jspecify.annotations.NullMarked;

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

// #region entity
@NullMarked
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
	protected Item getDefaultItem() {
		return ExampleModProjectile.HOT_TATER_ITEM;
	}
	// #endregion default_item

	// #region on_hit
	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		Level level = level();

		// Only modify the world on the server.
		if (!level.isClientSide()) {
			// If the projectile hits a block, place fire on the face it hit.
			BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());

			if (level.isEmptyBlock(pos)) {
				level.setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
			}

			// Remove the projectile after any hit, or it sinks into the ground forever.
			discard();
		}
	}
	// #endregion on_hit

	// #region on_hit_entity
	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);

		// Only modify the world on the server.
		if (!level().isClientSide()) {
			hitResult.getEntity().igniteForSeconds(5);
		}
	}
	// #endregion on_hit_entity
}
// #endregion entity
