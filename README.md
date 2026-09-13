# Shining Champions

为 Champions 非 Boss 生物提供客户端发光轮廓效果的 NeoForge/Forge Mod。

## 支持版本

- 当前分支：Minecraft 1.20.1（Forge）
- `1.21.1` 分支：Minecraft 1.21.1（NeoForge）
- `26.1` 分支：Minecraft 26.1（NeoForge）

该 Mod 只修改客户端渲染效果，不会向实体添加服务端发光效果。

## 构建

在对应分支的仓库根目录执行：

```text
./gradlew build
```

Windows PowerShell 可使用：

```text
.\gradlew.bat build
```

构建产物位于对应项目的 `build/libs/` 目录。

## 依赖

各版本工程会从 Curse Maven 获取对应版本的 Champions：

- 1.20.1：`curse.maven:champions-unofficial-1074990:6923340`
- 1.21.1 分支：`curse.maven:champions-unofficial-1074990:6923189`
- 26.1 分支：`curse.maven:champions-unofficial-1074990:7957521`
