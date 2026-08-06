# Library Architecture

`ComposeCollections` is designed with a **Foundation-First** philosophy. This ensures consistent behavior, high performance, and easy extensibility across all scrollable components.

## Core Layers

The library is structured into three main layers:

### 1. The Engine (`CollectionLayout`)
Located in `.collections.layout.foundation`, this is the lowest level of the UI.
- Handles the placement of content and navigation slots.
- Manages two distinct modes: **Stacked** (outside content) and **Overlay** (floating over content).
- Uses `Modifier.weight(1f)` and `wrapContentHeight/Width` to ensure list content occupies the available space while maintaining layout stability.

### 2. The Template (`CollectionScaffold`)
Also in `.collections.layout.foundation`, this acts as a high-level template.
- Injects the `CollectionTheme`.
- Orchestrates internal `CollectionRouter` calls to handle all possible button placements.
- Provides optional slots: `indicator` (for progress visuals) and `topOverlay` (for fixed elements).
- Bridges the generic layout with specific navigation triggers provided by the state.

### 3. Specialized Components
These are the public APIs developers interact with most:
- **Lists**: `CollectionPagedList` and `CollectionEdgedList`.
- **Grids**: `CollectionPagedGrid` and `CollectionEdgedGrid`.
- **Staggered Grids**: `CollectionPagedStaggeredGrid` and `CollectionEdgedStaggeredGrid`.
- Each component is a "thin wrapper" around the Scaffold, passing its specific scroll state and configurations.

## State Management (`CollectionState`)

We use a **State Hoisting** pattern via the `CollectionState` interface.
- **Contract-Based**: Components depend on the interface, not concrete implementations.
- **Derived Logic**: Standard implementations (`CollectionListState`, `CollectionGridState`, etc.) use `derivedStateOf` to efficiently track scroll position and toggle button visibility without causing global recompositions.

## Performance Optimization

- **Deferred State Reading**: Navigation visibility is passed as `() -> Boolean` lambdas. This keeps recomposition local to the `AnimatedVisibility` block.
- **Stability Tokens**: Design tokens are marked with `@Immutable` or `@Stable` to ensure the Compose compiler can skip unnecessary work.
- **Hardware Integration**: The Scaffold uses `onPreviewKeyEvent` to intercept and map hardware keys (`PageUp/Down`, `Home/End`) to scroll actions.

---

For a detailed mapping of component relationships and functional coverage, check the [API Blueprint & Technical Map](API_MAP.md).
