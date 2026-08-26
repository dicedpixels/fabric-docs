package com.example.docs.projectile;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import com.example.docs.ExampleMod;
import com.example.docs.item.ModItems;

// #region entrypoint
public class ExampleModProjectile implements ModInitializer {
	// #endregion entrypoint
	// #region identifier
	// Shared identifier for both the item and the entity type.
	public static final Identifier HOT_TATER_ID = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "hot_tater");
	// #endregion identifier

	// #region register_entity
	public static final EntityType<HotTaterEntity> HOT_TATER_ENTITY_TYPE = Registry.register(
					BuiltInRegistries.ENTITY_TYPE,
					HOT_TATER_ID,
					EntityType.Builder.<HotTaterEntity>of(HotTaterEntity::new, MobCategory.MISC)
									.sized(0.25F, 0.25F) // Hitbox width and height.
									.clientTrackingRange(4) // How far (in chunks) clients see the entity.
									.updateInterval(10) // Ticks between position updates sent to clients.
									.build(ResourceKey.create(Registries.ENTITY_TYPE, HOT_TATER_ID)));
	// #endregion register_entity

	// #region register_item
	public static final Item HOT_TATER_ITEM = Registry.register(
					BuiltInRegistries.ITEM,
					HOT_TATER_ID,
					new HotTaterItem(new Item.Properties().stacksTo(16)
									.setId(ResourceKey.create(Registries.ITEM, HOT_TATER_ID))));
	// #endregion register_item

	@Override
	public void onInitialize() {
		// #region creative_tab
		CreativeModeTabEvents.modifyOutputEvent(ModItems.CUSTOM_CREATIVE_TAB_KEY).register(tab -> {
			tab.accept(HOT_TATER_ITEM);
		});
		// #endregion creative_tab
	}
}
