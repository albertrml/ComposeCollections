# Migration Guide: v0.1.x to v0.2.x

Version 0.2.0 introduces significant structural improvements. This guide helps you update your project to the new API.

## 1. Package Naming (Breaking Change)

All packages have been renamed to remove redundancy and allow for future category expansions.

- **Old**: `br.com.arml.composecollections.composecollections.*`
- **New**: `br.com.arml.composecollections.scrollables.*`

**Action**: Use Android Studio's **Find and Replace** (Ctrl+Shift+R) to update your imports.

## 2. Function Unification

Orientation-specific functions have been unified into generic components.

| Old Component | New Component | Required Param |
| :--- | :--- | :--- |
| `VerticalPagedList` | `PagedList` | `layoutSpec = QuickNavLayoutSpec.Vertical()` |
| `HorizontalPagedList` | `PagedList` | `layoutSpec = QuickNavLayoutSpec.Horizontal()` |
| `VerticalEdgedGrid` | `EdgedGrid` | `layoutSpec = QuickNavLayoutSpec.Vertical()` |

**Action**: Update calls to `PagedList`, `EdgedList`, `PagedGrid`, or `EdgedGrid` and provide the appropriate `layoutSpec`.

## 3. Grid Parameter Renaming

To be orientation-agnostic, the `columns` parameter in `PagedGrid` and `EdgedGrid` has been renamed to `cells`.

- **Old**: `EdgedGrid(columns = GridCells.Fixed(3))`
- **New**: `EdgedGrid(cells = GridCells.Fixed(3))`

## 4. New State Controller

The `quickNavState` parameter now accepts the `QuickNavState` interface instead of concrete classes.

- If you were creating `QuickNavListState` manually, it still works as it now implements the interface.
- If you were using `rememberQuickNavListState`, the return type is now polymorphic.

## 4. Test Tags

If you have UI tests, note that the standard buttons now use tags provided by the theme (`QuickNavLabels`).

- Standard "Edged" Up Tag: `quickNavList_upButton_testTag`
- Standard "Paged" Up Tag: `pagedQuickNavList_upButton_testTag`

> [!TIP]
> You can now customize these tags globally via `QuickNavTheme`.

## 5. Semantic Unification (v0.2.3)

In version 0.2.3, we unified the navigation API to use direction-based terms (`Backward` and `Forward`) instead of location-based terms (`Start`, `End`, `Previous`, `Next`).

### State Changes

The following properties and methods in `QuickNavState` have been renamed:

- `showScrollToStart` / `showScrollToPrevious` -> **`showScrollToBackward`**
- `showScrollToEnd` / `showScrollToNext` -> **`showScrollToForward`**
- `animateScrollToStart` / `animateScrollToPreviousPage` -> **`animateScrollToBackward()`**
- `animateScrollToEnd` / `animateScrollToNextPage` -> **`animateScrollToForward()`**

> [!WARNING]
> The old methods are currently marked as `@Deprecated` and will be **permanently removed in version 0.3.0**. Please use the IDE's automated "Replace with" feature to update your code.
