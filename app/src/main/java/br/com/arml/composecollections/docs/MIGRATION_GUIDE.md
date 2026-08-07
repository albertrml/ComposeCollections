# Migration Guide: Evolution of ComposeCollections

This guide helps you update your project as the library evolves.

## v0.2.7 to v0.2.8: The Slot Era & Architectural Consolidation (Current)

Version 0.2.8 focuses on flexibility, semantic consistency, and a cleaner architecture.

### 1. Unified Slot API (Breaking Change)
We have renamed the slot parameters to align with our directional navigation API.

- **Old**: `leadingControl` / `trailingControl` (from beta v0.2.8)
- **New**: **`backwardControl`** / **`forwardControl`**
- **Action**: Update your parameter names when injecting custom UI.

### 2. Opt-in Navigation (Breaking Change)
To make the library a safe drop-in replacement for native `Lazy` components, navigation is now **disabled by default**.

- **Old**: Buttons showed automatically (`navigationAlignment` was `Bottom`).
- **New**: `navigationAlignment` now defaults to **`None`**.
- **Action**: Explicitly set `navigationAlignment = CollectionAlignment.Bottom` (or any other alignment) if you want to see the default buttons.

### 3. Layout Control (`expandLayout`)
You can now control if the container should "tightly" wrap your list or stretch to fill the available space.

- **`false` (Default)**: Container wraps content (prevents white space).
- **`true`**: Container stretches (pushes buttons to the edges of the screen).

### 4. Semantic Animation Integration
The `animationMode` parameter is now fully connected. Changing it to `Snap` or `Elastic` will automatically configure the internal `collectionState` with the correct physics.

---

## v0.2.6 to v0.2.7: Rebranding
... (previous history)
