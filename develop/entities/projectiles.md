---
title: Custom Projectiles
description: Learn how to add custom projectiles.
authors:
  - ayutac
  - cassiancc
  - ChampionAsh5357
  - dicedpixels
  - haykam
  - kanpov
  - NetUserGet
  - onlyspxctre
  - patrickmsm
  - tianjun
  - upcraftlp
resources:
  https://minecraft.wiki/w/Projectile: Projectiles - Minecraft Wiki
  https://docs.neoforged.net/docs/entities/#projectiles: Projectiles - NeoForge Docs
---

Projectiles are entities that can be thrown or fired by players or other entities. In this guide, we'll look into implementing a simple projectile like a snowball.

We'll call our projectile a Hot Tater. It will be a potato that sets the block or entity it hits on fire.

::: info PREREQUISITES

Creating a projectile requires you to register an item as well as an entity, therefore we suggest going through the [Creating Your First Item](../items/first-item) and [Creating Your First Entity](./first-entity) guides.

:::

## Creating the Projectile Entity {#creating-the-projectile-entity}

Let's create a `HotTaterEntity` by extending `ThrowableItemProjectile`. This class should be in your `main` source set.

The `ThrowableItemProjectile` class handles the physics and the item form of the projectile.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#entity

There's quite a lot happening here. Let's look at the important code sections.

### Constructors {#constructors}

We define 3 constructors. They're used by entity registration, projectile spawning and projectile conversion respectively.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#constructors

### Overriding `getDefaultItem()` {#override-get-default-item}

Defines the item form of this projectile.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#default_item

::: info

Your IDE might tell you that it cannot resolve the item: we will create it soon, in the [Registering the Item](#registering-the-item) section.

:::

### Overriding `onHitBlock()` {#override-on-hit-block}

Defines the behavior when this projectile hits a block. We check where the projectile has hit and then set the hit face of that block on fire. This logic is handled on the server side.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_block

### Overriding `onHitEntity()` {#override-on-hit-entity}

Defines the behavior when this projectile hits an entity. We set the entity that was hit on fire for 5 seconds.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_entity

### Overriding `onHit()` {#override-on-hit}

Defines the behavior when this projectile hits anything, whether a block or an entity. We will use this to discard the projectile, so that it is removed on hit; without this, the projectile would just keep going.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit

## Creating the Item {#creating-the-item}

We register a simple item. Since we need to implement the throwing logic, our class `HotTaterItem` will extend `Item` and implement `ProjectileItem`. This class should be in your `main` source set.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#item

It's a standard item implementation, with some special methods from `ProjectileItem`. Let's look at them:

### Overriding `asProjectile()` {#override-as-projectile}

This method converts the item into its entity form.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#as_projectile

### Overriding `use()` {#override-use}

Defines the action that happens when the item is used. In our case, we call the `Projectile.spawnProjectileFromRotation()` utility method to spawn the projectile.

In addition to the standard parameters (level, item stack, and player), this utility method takes three additional floats:

- **Y-Offset**: Rotation in the y direction (looking up or down).
- **Power**: Scales how fast the projectile moves in blocks.
- **Uncertainty**: Range of directions that the projectile can randomly go in when shot.

Finally, we award the `ITEM_USED` stat, consume one item from the stack and mark the interaction as successful.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#use

## Entity Renderer {#entity-renderer}

Like most entities, your projectile will also need a renderer to render its visuals while it's on the move. For this guide, we'll be using Minecraft's built-in `ThrownItemRenderer`. If you need a custom renderer, you may implement your own by extending `EntityRenderer`. Like all kinds of rendering, this code will be in your `client` source set.

## Registration {#registration}

Now that we have a projectile entity and an item, we need to register them. In addition to these, we'll also register the entity renderer.

For this example, our registration code will be in our main and client initializers.

For convenience, we define a shared identifier for both the entity and the item.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/ExampleModProjectile.java#identifier

**Initializer:**

<<< @/reference/latest/src/main/java/com/example/docs/projectile/ExampleModProjectile.java#entrypoint

**Client Initializer:**

<<< @/reference/latest/src/client/java/com/example/docs/projectile/ExampleModProjectileClient.java#entrypoint

Let's analyze the code.

### Registering the Entity {#registering-the-entity}

Like other entities, we use `EntityType.Builder`. We call the `of()` method with a hitbox size, client tracking range and tick update interval. Finally we build the entity type by passing a `ResourceKey`. This entity type is then registered to the `ENTITY_TYPE` registry. This code will be in your `main` source set.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/ExampleModProjectile.java#register_entity

### Registering the Item {#registering-the-item}

We register the item using the `register` method from our `ModItems` class, created in the [Creating Your First Item](../items/first-item#preparing-your-items-class) guide. This code will also be in your `main` source set.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/ExampleModProjectile.java#register_item

We also add the item to a creative tab for easy access.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/ExampleModProjectile.java#creative_tab

### Registering the Renderer {#registering-the-renderer}

We use the Minecraft-provided `ThrownItemRenderer` for our entity. This is the same renderer used by throwable items like the snowball.

<<< @/reference/latest/src/client/java/com/example/docs/projectile/ExampleModProjectileClient.java#renderer

## Finalizing the Item {#finalizing-the-item}

At this point, you can obtain the projectile item in game and test out the functionality.

However, you'll notice it still doesn't have a [model](../items/first-item#adding-a-model), [texture](../items/first-item#adding-a-texture), [client item](../items/first-item#creating-the-client-item) or a [name](../items/first-item#naming-the-item). Let's create the files using the identifier `hot_tater` in order to render everything properly. An example texture is provided below.

<DownloadEntry visualURL="/assets/develop/projectiles/hot_tater.png" downloadURL="/assets/develop/projectiles/hot_tater_small.png">Texture</DownloadEntry>

Now you can test your projectile in-game:

<VideoPlayer src="/assets/develop/projectiles/hot-tater.mp4">A Hot Tater setting a villager on fire</VideoPlayer>
