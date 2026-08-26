package com.example.docs.projectile;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.fabricmc.api.ClientModInitializer;

// #region entrypoint
public class ExampleModProjectileClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// region renderer
		EntityRenderers.register(ExampleModProjectile.HOT_TATER_ENTITY_TYPE, ThrownItemRenderer::new);
		// endregion renderer
	}
}
// #endregion entrypoint
