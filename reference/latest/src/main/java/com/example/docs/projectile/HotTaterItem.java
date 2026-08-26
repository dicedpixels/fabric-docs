package com.example.docs.projectile;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

// #region item
public class HotTaterItem extends Item implements ProjectileItem {
	// #endregion item
	public HotTaterItem(Properties properties) {
		super(properties);
	}

	// #region as_projectile
	@Override
	public @NonNull Projectile asProjectile(@NonNull Level level,
											@NonNull Position position,
											@NonNull ItemStack itemStack,
											@NonNull Direction direction) {
		// Used when the item is shot from a dispenser instead of thrown by a player.
		return new HotTaterEntity(level, position.x(), position.y(), position.z(), itemStack);
	}
	// #endregion as_projectile

	// #region use
	@Override
	public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		// Spawn the projectile on the server only.
		if (level instanceof ServerLevel serverLevel) {
			// Pitch offset = 0, power = 1.5, inaccuracy = 1.0.
			Projectile.spawnProjectileFromRotation(HotTaterEntity::new,
													serverLevel,
													itemStack,
													player,
													0.0F,
													1.5F,
													1.0F);
		}
		itemStack.consume(1, player);
		return InteractionResult.SUCCESS;
	}
	// #endregion use
}
