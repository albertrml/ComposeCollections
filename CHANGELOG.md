# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
