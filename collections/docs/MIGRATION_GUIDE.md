# Migration Guide: Evolution of ComposeCollections

This guide helps you update your project as the library evolves.

## v0.2.8 to v0.2.9: Modular Separation (Current)

Version 0.2.9 focus on architectural hygiene, separating the library core from the sample application.

### 1. New Artifact Path
The core library now lives in its own module.

- **Action**: Update your dependency reference if you are using project-level dependencies.
- **Library Artifact**: `br.com.arml.composecollections:collections:0.2.9`.

### 2. Documentation Location
All technical documentation has been moved inside the library module.

- **Path**: `collections/docs/`

---

## v0.2.7 to v0.2.8: The Slot Era & Architectural Consolidation

### 1. Unified Slot API (Breaking Change)
Slot parameters were renamed to align with directional navigation.

- **Old**: `leadingControl` / `trailingControl`
- **New**: **`backwardControl`** / **`forwardControl`**

### 2. Opt-in Navigation (Breaking Change)
Navigation is now **disabled by default**.

- **New**: `navigationAlignment` now defaults to **`None`**.
- **Action**: Explicitly set `navigationAlignment = CollectionAlignment.Bottom` to show buttons.

### 3. Layout Control (`expandLayout`)
- **`false` (Default)**: Container wraps content (Tight).
- **`true`**: Container stretches (Stretch).

---

## v0.2.6 to v0.2.7: Rebranding
... (previous content)
