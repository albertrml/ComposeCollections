# Library Architecture

`ComposeCollections` is designed with a **Foundation-First** philosophy. This ensures consistent behavior, high performance, and easy extensibility across all scrollable components.

## Core Layers

The library is structured into three main layers:

### 1. The Engine (`CollectionLayout`)
Located in `.collections.layout.foundation`, this is the lowest level of the UI.
- Handles the placement of content and navigation slots.
- Manages two distinct modes: **Stacked** (outside content) and **Overlay** (floating over content).
- Implements the **Expansion Policy**: can either "tightly" wrap content or stretch to fill available space based on the `expandLayout` parameter.

### 2. The Template (`CollectionScaffold`)
Also in `.collections.layout.foundation`, this acts as a high-level template.
- Injects the `CollectionTheme`.
- Orchestrates the **Slot API**: manages the precedence between `leadingControl/trailingControl` and default navigation panels.
- Bridges the generic layout with specific navigation triggers provided by the state and hardware input.

### 3. Specialized Components
These are the public APIs developers interact with most:
- **Lists**: `CollectionPagedList` and `CollectionEdgedList`.
- **Grids**: `CollectionPagedGrid` and `CollectionEdgedGrid`.
- Each component is a "thin wrapper" around the Scaffold, acting as a factory for default controls via `CollectionDefaults`.

## State Management (`CollectionState`)

We use a **State Hoisting** pattern via the `CollectionState` interface.
- **Contract-Based**: Components depend on the interface, not concrete implementations.
- **Derived Logic**: Standard implementations use `derivedStateOf` to efficiently track scroll position and toggle button visibility.

## The Slot API (Inversion of Control)

In v0.2.8, we introduced full inversion of control. 
- **Leading/Trailing Controls**: Developers can replace the library's default buttons with any Composable.
- **Atomic Reuse**: The library provides `CollectionScrollButton` so custom UIs can reuse the built-in visibility and scroll logic.

---

For a detailed mapping of component relationships and functional coverage, check the [API Blueprint & Technical Map](API_MAP.md).
