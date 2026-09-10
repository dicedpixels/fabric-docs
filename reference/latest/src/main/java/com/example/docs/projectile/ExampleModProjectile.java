package com.example.docs.projectile;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import com.example.docs.ExampleMod;
import com.example.docs.item.ModItems;

// #region entrypoint
public class ExampleModProjectile implements ModInitializer {
	// #region identifier
	// Identifier for the item. The entity type uses the same path via ModEntityTypes.
	public static final Identifier HOT_TATER_ID = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "hot_tater");
	// #endregion identifier

	// #region register_item
	public static final Item HOT_TATER_ITEM = ModItems.register(
					ResourceKey.create(Registries.ITEM, HOT_TATER_ID),
					HotTaterItem::new,
					new Item.Properties().stacksTo(16));
	// #endregion register_item

	@Override
	public void onInitialize() {
		CreativeModeTabEvents.modifyOutputEvent(ModItems.CUSTOM_CREATIVE_TAB_KEY).register(tab -> {
			tab.accept(HOT_TATER_ITEM);
		});
	}
}
// #endregion entrypoint
