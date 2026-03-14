# Productive Cows - Agent Guide

This document provides essential information for AI agents (and developers) working on the Productive Cows Minecraft mod.

## Project Overview
Productive Cows is a NeoForge mod focused on resource generation through specialized cows. These cows produce "flavored milk" (fluid) containing the essence of resources (e.g., Iron Cows produce iron-flavored milk).
- **Core Mechanic:** Resource cows -> Flavored milk -> Milk filtering multiblock (2x2x2) -> Resources.
- **Power:** Integrates with Create mod "spin energy".
- **Dynamic System:** Cow types are defined via JSON files in `data/productivecows/cow_types/`.
- **Breeding:** Parent options in JSON files define breeding outcomes (e.g., Lapis + Red -> Redstone).

## Build & Development Commands
This project uses Gradle with the NeoForge moddev plugin.

- **Build Mod:** `./gradlew build`
- **Run Client (Dev):** `./gradlew runClient`
- **Run Server (Dev):** `./gradlew runServer`
- **Generate Data:** `./gradlew runData` (Generates resources like tags, recipes, and models)
- **Run All Tests:** `./gradlew test` (Note: GameTests are primarily used for logic verification)
- **Run Single Test:** `./gradlew test --tests "net.mojumo.productivecows.package.ClassName.methodName"`
- **Clean Build:** `./gradlew clean build`

## Code Style & Conventions

### 1. Java Standards
- **Language Level:** Java 21 (required for NeoForge 20.x+).
- **Naming:** 
  - Classes: `PascalCase` (e.g., `ProductiveCowEntity`)
  - Methods/Variables: `camelCase` (e.g., `getCowType`)
  - Constants: `SCREAMING_SNAKE_CASE` (e.g., `MODID`)
- **Formatting:** Standard Java conventions. 4-space indentation. No trailing spaces.
- **Imports:** Avoid wildcard imports (`import net.minecraft.*`). Group imports: `java`, then `net.minecraft`, then `net.neoforged`, then local mod classes.

### 2. Minecraft/NeoForge Specifics
- **Registries:** Use `DeferredRegister` for all registry objects (Items, Blocks, Entities, Fluids).
  - Location: `net.mojumo.productivecows.item.ModItems`, `net.mojumo.productivecows.block.ModBlocks`, etc.
- **Resources:** Use `ResourceLocation.parse("productivecows:name")` for identifying assets and registry entries.
- **Networking/Data:** Use `SynchedEntityData` for entity properties that must sync between server and client (see `ProductiveCowEntity`).
- **NBT:** Always override `addAdditionalSaveData` and `readAdditionalSaveData` for persistent entity data.

### 3. Error Handling
- Use `ProductiveCows.LOGGER` for logging.
- Prefer `LOGGER.error("Message", exception)` to provide full stack traces in dev logs.
- Validation: Check for null when retrieving objects from registries or JSON parsing.

### 4. JSON Cow System
- Cow types are loaded dynamically. When adding features, ensure they respect the `CowType` record/class structure.
- JSON location: `src/main/resources/data/productivecows/cow_types/`
- Structure: `{ "id": "...", "material": "...", "texture": "..." }`

## Testing Philosophy
- **Unit Tests:** Use for utility methods and logic that doesn't require a full Minecraft environment.
- **GameTests:** Use for multi-block logic, entity behavior, and interaction tests. Register in `build.gradle` via `neoforge.enabledGameTestNamespaces`.

## Interaction Rules
- **Proactiveness:** When adding a new cow type, ensure you consider its texture, material, spawning/breeding logic, and data generation.
- **Safety:** Do not modify the `MODID` or core registry bootstrap logic without explicit instructions.
- **Create Integration:** Keep Create mod compatibility in mind for automation features.
