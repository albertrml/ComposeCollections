# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
