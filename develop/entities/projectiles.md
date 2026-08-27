---
title: Projectiles
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
  https://docs.neoforged.net/docs/entities/#projectiles: Projectiles - NeoForge Docs (except Neo exclusives)
---

Projectiles are entities that can be thrown or fired by players or other entities. In this guide, we'll look into implementing a simple projectile like a snowball.

We'll call our projectile a Hot Tater. It will be a potato that sets the block or entity it hits on fire.

## Prerequisites {#prerequisites}

Creating a projectile requires you to register an item as well as an entity. Therefore we suggest going through the [Creating Your First Item](../items/first-item) and [Creating Your First Entity](./first-entity) guides.

## Creating the Projectile Entity {#creating-the-projectile-entity}

Let's create `HotTaterEntity` by extending `ThrowableItemProjectile`. This class should be in your `main` source set.

The `ThrowableItemProjectile` class handles the physics logic and knows how to store the item form of the projectile.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#entity

There's quite a lot happening here. Let's look at the important code sections.

### Constructors {#constructors}

We define 3 constructors. They're used by entity registration, projectile spawning and projectile conversion respectively.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#constructors

### Overrides {#projectile-entity-overrides}

We will be overriding `getDefaultItem()`, `onHitBlock()`, `onHitEntity()` and `onHit()`.

**`getDefaultItem()`**

Defines the item form of this projectile.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#default_item

::: info

We will register our item in a bit. You may also register the item first. See the [Registering the Item](#registering-the-item) section.

:::

**`onHitBlock()`**

Defines the behavior when this projectile hits a block. We check where the projectile has hit and then set the top face of that block on fire. This logic is handled on the server side.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_block

**`onHitEntity()`**

Defines the behavior when this projectile hits an entity. We set the entity that was hit on fire for 5 seconds.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_entity

**`onHit()`**

Defines the behavior when this projectile hits anything, whether a block or an entity. Vanilla projectiles like the snowball discard themselves here so that the projectile is always removed on hit. Without this, a projectile that hits an entity would just keep going.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit

## Creating the Item {#creating-the-item}

We register a simple item. Since we need to implement the throwing logic, our class `HotTaterItem` will extend `Item` and implement `ProjectileItem`. This class should be in your `main` source set.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#item

It's a simple item implementation with a standard constructor. But let's analyze the rest of the implementation.

### Overrides {#item-overrides}

We override `asProjectile()` and `use()`.

**`asProjectile()`**

This method converts the item into its entity form.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#as_projectile

**`use()`**

Defines the action that happens when the item is used. In our case, we use the `Projectile.spawnProjectileFromRotation()` utility method to spawn the projectile. In addition to the obvious parameters of the level, item stack, and player, this utility method takes three floats dictating the y-offset, power, and uncertainty.

- **Y-Offset**: Rotation in the y direction (looking up or down).
- **Power**: Scales how fast the projectile moves in blocks.
- **Uncertainty**: Scales the randomness in what direction the projectile goes when shot.

Finally, we consume one item from the stack and mark the interaction as successful.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#use

## Entity Renderer {#entity-renderer}

Like most entities, your projectile will also need a renderer to render its visuals while it's on the move. For this guide, we'll be using Minecraft's built-in `ThrownItemRenderer`. If you need a custom renderer, you may implement your own by extending `EntityRenderer`. Like all kinds of rendering, this code will be in your `client` source set.

## Registration {#registration}

Now that we have a projectile entity and an item, we need to register them. In addition to these, we'll also register the entity renderer.

For this example, our registration code will be in our normal and client initializers. You may organize your code as you see fit.

**Initializer:**

<<< @/reference/latest/src/main/java/com/example/docs/projectile/ExampleModProjectile.java#entrypoint

**Client Initializer:**

<<< @/reference/latest/src/client/java/com/example/docs/projectile/ExampleModProjectileClient.java#entrypoint

Let's analyze the code.

For convenience, we define a shared identifier for both the entity and the item.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/ExampleModProjectile.java#identifier

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

At this point, you can obtain the projectile item in game and test out the functionality.

## Finalizing the Item {#finalizing-the-item}

Even though our projectile works, it still doesn't have a model, texture, client item or a name. Items require a [model](../items/first-item#adding-a-model), [texture](../items/first-item#adding-a-texture), and [client item](../items/first-item#creating-the-client-item) with the name `hot_tater` in order to render correctly. You'll also want a [translation](../items/first-item#naming-the-item) to give the item a proper name. An example texture is provided below.

<DownloadEntry visualURL="/assets/develop/projectiles/hot_tater.png" downloadURL="/assets/develop/projectiles/hot_tater_small.png">Texture</DownloadEntry>

Now you can test your projectile in game.

<VideoPlayer src="/assets/develop/projectiles/hot-tater.mp4">A Hot Tater setting a villager on fire</VideoPlayer>
