# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.6] - 2026-08-06

### Added
- **API Consolidation**: `QuickNavScaffold` now accepts a single `quickNavState: QuickNavState` parameter instead of multiple lambdas, leading to a cleaner and more robust internal architecture.
- **Performance Excellence**: Enabled **Compose Compiler Metrics** to monitor library health. Confirmed that all main components are now 100% `skippable`.
- **Stability Tokens**: Applied `@Immutable` and `@Stable` annotations across all design tokens (`Labels`, `Icons`, `Dimensions`) to prevent unnecessary recompositions.
- **IDE Productivity**: Introduced `QuickNavStateProvider`, allowing developers to preview various scroll states (Start, Middle, End) directly in the Android Studio Design panel.
- **Semantic Headers**: Utilized the `isHeader` property in Grids to automatically apply `heading()` semantics, improving accessibility for TalkBack users.
- **Educational Guides**: Added new documentation files: `COMPOSE_COMPILER_METRICS_GUIDE.md`, `API_DESIGN_GUIDELINES.md`, and `COMPOSE_GUIDELINES.md`.

### Changed
- **Internal Refactoring**: Extracted repetitive grid rendering logic into `renderQuickNavItems` utility functions to improve maintainability and readability.
- **Simplified Signatures**: Internal components like `NavigationRouter` now consume theme data directly from `QuickNavTheme`, eliminating redundant parameter passing.
- **Layout Expansion**: Updated `QuickNavLayout` to default to `fill = true`, ensuring components occupy the available space effectively.

### Fixed
- **Horizontal Staggered Spacing**: Resolved a visual issue where `LazyHorizontalStaggeredGrid` would show excessive empty space between lanes by improving space distribution in the Scaffold and updating samples to use adaptive cell sizing.

## [0.2.5] - 2026-08-05

### Added
- **Grid Sticky Headers**: Introduced a custom `QuickNavGridScope` that brings `stickyHeader` support to `PagedGrid`, `EdgedGrid`, and their staggered variants.
- **Floating Overlay Logic**: Implemented a virtual sticky header system using a floating overlay that synchronizes with the grid's scroll state.
- **New Scaffold Slot**: Added `topOverlay` to `QuickNavScaffold` to host fixed UI elements like the new grid headers.
- **Comprehensive Grid Support**: Sticky headers are now fully functional in `LazyVerticalGrid`, `LazyHorizontalGrid`, and Pinterest-style `StaggeredGrids`.

### Fixed
- **Header Sync**: Added a dedicated test tag `QuickNavScaffoldHeaderOverlayTestTag` to ensure reliable instrumentation of floating headers.
- **Scroll Observation**: Refined the header switcher logic to correctly detect and swap headers during high-velocity scrolls.

## [0.2.4] - 2026-08-05

### Added
- **A11y & Hardware Input**: Full support for physical keyboards and D-pads (TV/Desktop).
- **Universal Shortcuts**:
    - `PageUp` / `PageDown`: Navigate through pages.
    - `Home` / `End`: Jump directly to the start or end of the collection.
- **State Expansion**: Added `animateScrollToStart` and `animateScrollToEnd` to `QuickNavState` for precise absolute navigation.
- **Accessibility Sample**: Added a new demonstration screen in the Gallery App for testing hardware peripherals.

### Fixed
- **Input Priority**: Switched to `onPreviewKeyEvent` in the Scaffold to ensure the library's navigation logic has priority over default component behaviors.
- **Focus Management**: Ensured the root container is focusable, enabling immediate interaction with hardware devices.

## [0.2.3] - 2026-08-05

### Added
- **Animation Presets**: Introduced `QuickNavAnimationMode` (Snap and Elastic) with built-in `AnimationSpec` presets in `QuickNavAnimationDefaults`.
- **Custom Scroll Physics**: States now support a custom `scrollAnimationSpec` to define the "feel" of programmatic navigation.
- **Animation Showcase**: Added a new category to the Gallery App to demonstrate the difference between Snap (precise) and Elastic (bouncy) scroll.

### Changed
- **Semantic Unification**: Unified the navigation API under direction-based terms: `Backward` and `Forward`.
- **API Simplification**: Reduced the complexity of the `QuickNavState` interface by merging redundant properties and methods.
- **State Evolution**: Refactored `QuickNavListState`, `QuickNavGridState`, and `QuickNavStaggeredGridState` to support the new unified API and custom animations.

### Deprecated
- **Legacy Navigation Methods**: Methods like `animateScrollToStart`, `animateScrollToEnd`, `animateScrollToPreviousPage`, and `animateScrollToNextPage` are now deprecated and scheduled for removal in v0.3.0.

## [0.2.2] - 2026-08-04

### Added
- **Visual Progress Indicators**: Introduced `scrollProgress` to the `QuickNavState` interface, allowing real-time tracking of scroll position.
- **Scroll Indicators**: Added `QuickNavLinearIndicator`, a customizable progress bar that adapts to both vertical and horizontal layouts.
- **Scaffold Slot**: Added a dedicated `indicator` slot to `QuickNavScaffold` for easier integration of progress visuals.
- **Feature Flag**: New `showIndicator: Boolean` parameter added to all public List and Grid components for instant progress bar activation.

### Changed
- **Internal Refactoring**: Renamed internal assembly function to `QuickNavNavigationFrame` and marked it as `internal` to further protect the public API.

### Fixed
- **Precision**: Improved scroll progress calculation logic to reach exactly 1.0 when the last item is fully visible.

## [0.2.1] - 2026-07-31

### Added
- **Staggered Grid Support**: Introduced `PagedStaggeredGrid` and `EdgedStaggeredGrid` for irregular grid layouts (e.g., Pinterest style).
- **Staggered State**: Implemented `QuickNavStaggeredGridState` for specialized scroll tracking in staggered containers.
- **New Samples**: Added Pinterest-style vertical and horizontal staggered grid examples to the Gallery App.
- **Enhanced Testing**: Added instrumentation tests for all staggered grid variants.

## [0.2.0] - 2026-07-31

### Added
- **Grid Support**: Introduced `PagedGrid` and `EdgedGrid` components for fast navigation in multi-column collections.
- **Horizontal Orientation**: Full support for `LazyRow` and `LazyHorizontalGrid` via generic `layoutSpec` parameter.
- **Architecture**: Implemented `QuickNavScaffold` and `QuickNavLayout` as the foundation for all scrollable components, ensuring consistent behavior and layout stability.
- **State Abstraction**: Introduced the `QuickNavState` interface, allowing developers to implement custom navigation logic and scroll behaviors.
- **Comprehensive Documentation**: Added specialized guides in `docs/` covering architecture, customization, and migration.

### Changed
- **Package Renaming**: Migrated all components from `...composecollections` to `...scrollables` for better categorization and future-proofing.
- **API Unification**: Unified orientation-specific functions into single generic components (e.g., `VerticalPagedList` is now `PagedList` with `QuickNavLayoutSpec.Vertical`).

### Fixed
- **Layout Deformation**: Resolved issues where navigation panels would stretch the scrollable content in certain configurations using `fill = false` and `wrapContent` modifiers.
- **Testing**: Unified the test suite to cover all orientations and layout modes.

## [0.1.2] - 2026-07-28

### Changed
- **Refactoring**: Improved `QuickNavList` architecture using a template-based pattern for better reuse.
- **API Evolution**: Components now support custom labels and content descriptions via `QuickNavLabels`.

### Fixed
- **UI Transitions**: Replaced abrupt navigation button visibility changes with smooth `AnimatedVisibility` (fade + expansion).
- **UX**: Refined scroll visibility logic to be more responsive, showing return buttons as soon as the list leaves the start position.

### Added
- **Theming**: Introduced `QuickNavTheme` (powered by `CompositionLocal`) for global configuration of icons, labels, and transitions.

## [0.1.1] - 2026-07-27

### Fixed
- Fixed Maven publication conflict in GitHub Packages.

## [0.1.0] - 2026-07-27

### Added
- **QuickNav API**: Base infrastructure for fast navigation in collections.
- **QuickNavList**: An enhanced `LazyColumn` with "Jump to Top" and "Jump to Bottom" actions.
- **PagedQuickNavList**: Navigation component that scrolls by "page" (visible viewport).
- **Smart Visibility**: Navigation buttons that react to scroll position and velocity.
- **GitHub Packages Integration**: Initial configuration for publishing to GitHub Packages.
- **Project Structure**: Multi-module ready setup with documentation.
