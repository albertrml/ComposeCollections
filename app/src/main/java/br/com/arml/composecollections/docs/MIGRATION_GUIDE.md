# Migration Guide: Evolution of ComposeCollections

This guide helps you update your project as the library evolves.

## v0.2.6 to v0.2.7: Rebranding (Breaking Change)

In version 0.2.7, the library transitioned from a utility focused on "Quick Navigation" to a comprehensive "Collection Containers" framework.

### 1. Package Renaming

- **Old**: `br.com.arml.composecollections.scrollables.*`
- **New**: `br.com.arml.composecollections.collections.*`

### 2. Component Renaming

All components and classes starting with `QuickNav` have been renamed to `Collection`.

| Old Name | New Name |
| :--- | :--- |
| `QuickNavScaffold` | `CollectionScaffold` |
| `QuickNavState` | `CollectionState` |
| `QuickNavTheme` | `CollectionTheme` |
| `PagedList` | `CollectionPagedList` |
| `EdgedGrid` | `CollectionEdgedGrid` |
| ... | ... |

### 3. Cleanup of Legacy Methods

All methods marked as `@Deprecated` in v0.2.3 and v0.2.6 (like `animateScrollToStart`, `animateScrollToNextPage`) have been **removed**. Use the unified direction-based API:
- `animateScrollToBackward()`
- `animateScrollToForward()`

---

## Older Migrations (Reference)

### v0.1.x to v0.2.0: Structural Improvements
... (previous content)
