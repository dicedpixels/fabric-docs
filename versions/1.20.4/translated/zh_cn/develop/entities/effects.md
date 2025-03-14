---
title: 状态效果
description: 学习如何添加自定义状态效果。
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
authors-nogithub:
  - siglong
  - tao0lu

search: false
---

状态效果，又称效果，是一种可以影响实体的条件。 它们的性质可以是正面的，负面的或者中性的。 游戏本体通过许多不同的方式应用这些效果，如食物和药水等等。

命令 `/effect` 可被用于给实体应用效果。

## 自定义状态效果

在这篇教程中我们将加入一个叫 _土豆_ 的新状态效果，它会每游戏刻给你 1 点经验。

### 继承 `StatusEffect`

让我们通过继承所有状态效果的基类 `StatusEffect` 来创建一个自定义状态效果类。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### 注册你的自定义状态效果

与注册方块和物品类似，我们使用 `Registry.register` 将我们的自定义状态效果注册到 `STATUS_EFFECT` 注册表。 这可以在我们的模组入口点内完成。 这可以在我们的模组入口点内完成。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### 本地化与纹理

您可以为您的状态效果指定名称并提供一个纹理（texture）图标，这将显示在玩家背包中。

#### **纹理**

状态效果图标是 18x18 的 PNG。 将您的自定义图标放在： 将您的自定义图标放在：

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

![在玩家背包中的效果](/assets/develop/tater-effect.png)

#### **翻译**

像其它翻译一样，您可以添加一个 ID 格式的条目 `"effect.mod-id.<effect-identifier>": "Value"` 到语言文件中。

::: code-group

```json[assets/fabric-docs-reference/lang/en_us.json]
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### 测试

使用命令 `/effect give @p fabric-docs-reference:tater` 为玩家提供 Tater 效果。
使用命令 `/effect give @p fabric-docs-reference:tater` 为玩家提供 Tater 效果。 使用 `/effect clear` 移除该效果。

::: info
要创建使用此效果的药水，请参阅[药水](../items/potions)指南。
:::
