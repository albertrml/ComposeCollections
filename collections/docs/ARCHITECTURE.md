# Library Architecture (v0.2.9)

`ComposeCollections` is designed with a **Foundation-First** and **Modular** philosophy. This ensures that the production code is strictly decoupled from demonstration samples, allowing for a cleaner and more efficient distribution.

## Core Modules

### 📦 1. The `:collections` Module (Production)
This is the heart of the framework. It contains no application logic, only pure library components.
- **Production Engine**: All scaffold and layout logic.
- **Resources**: Dedicated `strings.xml` for library-specific labels and test tags.
- **Unit & Instrumentation Tests**: Focused on the integrity of the API components.

### 🖼️ 2. The `:app` Module (Gallery)
A consumer application that showcases the library's capabilities.
- **Clean Consumption**: It depends on `:collections` via `implementation(project(":collections"))`.
- **Sample Repository**: Hosts all implementation examples and the `MainActivity` dashboard.

## Core Layers (inside :collections)

### 1. The Engine (`CollectionLayout`)
Handles atomic placement. Manages **Stacked** and **Overlay** modes, and the **Expansion Policy** (Tight vs Stretch).

### 2. The Template (`CollectionScaffold`)
Orchestrates the **Slot API** and bridges generic layouts with scroll navigation triggers.

### 3. State Management (`CollectionState`)
Uses a **State Hoisting** pattern. Standard implementations use `derivedStateOf` to track scroll progress and toggle visibility without excessive recompositions.

## The Slot API (Inversion of Control)

Introduced in v0.2.8 and refined in v0.2.9:
- **backwardControl / forwardControl**: Developers can replace default buttons with custom Composables.
- **Atomic Components**: `CollectionScrollButton` is provided for building custom control panels with built-in library logic.

---

For a detailed mapping of component relationships and functional coverage, check the [API Blueprint & Technical Map](API_MAP.md).
