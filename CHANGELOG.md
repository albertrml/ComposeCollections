# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.9] - 2026-08-07

### Added
- **Multi-Module Architecture**: Successfully separated the production library from the sample application.
- **Dedicated :collections Module**: The core framework now lives in its own Android Library module, ensuring a clean and lightweight distribution.
- **Resource Encapsulation**: Moved all library-specific resources (strings, test tags) to the `:collections` module for self-contained functionality.
- **Sample Reorganization**: Moved all demonstration code to the `:app` module, transforming it into a real-world consumer of the library.

### Changed
- **Documentation Migration**: Technical guides and API maps moved to `collections/docs/` to keep knowledge close to implementation.
- **Artifact Rebranding**: The primary artifact name for the core library is now **`collections`**.

### Fixed
- **Clean Distribution**: The published library no longer contains application-level code like `MainActivity` or sample assets.
- **Test Integrity**: Validated the complete suite of 20 instrumentation tests in the new isolated library environment.

## [0.2.8] - 2026-08-07

### Added
- **Slot API (Semantic Navigation)**: Introduced `backwardControl` and `forwardControl` slots. Developers can inject custom UI (FABs, SearchBars) that override library defaults with total sovereignty.
- **Architectural Consolidation**: Unified generalist containers (`CollectionList`, `Grid`, `StaggeredGrid`) with their specialist "Sugar Functions" (`CollectionPagedList`, etc.) in single files for better DX.
- **Opt-in Navigation UI**: Navigation controls now default to `None`. Components act as pure "Lite" containers by default, ideal for drop-in replacement of native Lazy components.
- **Physical Feel Control**: Fully connected the `animationMode` parameter. Switching between `Default`, `Snap`, and `Elastic` now automatically reconfigures scroll physics.
- **Centralized Defaults**: Created `CollectionDefaults` with `DefaultNavigationControl` factory to ensure UI consistency across all containers.
- **Custom Control Sample**: Added a Gallery example showcasing a list controlled by a **Floating Action Button (FAB)** via the Slot API.

### Changed
- **Parameter Normalization**: Standardized parameter order across the library (Modifier -> State -> Mode -> Animation -> Content).
- **Layout Expansion Logic**: Formalized the `expandLayout` toggle to support both "Tight" (content-wrapped) and "Stretch" (full-screen) footprints.

### Fixed
- **Slot Priority Logic**: Resolved a bug where custom controls would be ignored if navigation alignment didn't match perfectly. Slots are now strictly sovereign.
- **Orientation-Agnostic Controls**: Fixed rendering of controls in crossed orientations (e.g., vertical controls for horizontal lists).
- **Test Integrity**: achieved 100% pass rate with 20 exaustive tests covering state, slots, hardware, and layout expansion.

## [0.2.7] - 2026-08-06
... (previous history)
