# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.3.0] - 2026-08-13

### Added
- **High-Precision Telemetry**: Re-engineered `scrollProgress` to use pixel-based calculations. It now handles items of varying sizes with linear accuracy.
- **Advanced Indicators**: 
    - `CollectionScrollbar`: A modern, discretely animated scrollbar that reacts to movement.
    - `CollectionPageCounter`: A floating "pill" indicator showing "Current Page / Total Pages".
- **Enhanced State API**: Added `isScrolling`, `currentPage`, and `totalPages` to the `CollectionState` interface.
- **Dynamic Orientation Support**: All state telemetry is now orientation-aware, correctly calculating progress in both Vertical and Horizontal layouts.
- **Showcase Sample**: Added "Advanced Telemetry" screen in the Gallery App demonstrating the synergy between the new indicators and precision state.

### Changed
- **Indicator Visibility**: Promoted `CollectionLinearIndicator` to a public component with customizable colors.

### Fixed
- **Telemetry Drift**: Resolved issues where progress would be inaccurate in lists with large or inconsistent item heights.

## [0.2.9] - 2026-08-07

### Added
- **Multi-Module Architecture**: Separated the library core from the sample application.
- **Dedicated :collections Module**: Ensured a clean, lightweight distribution focused on production code.
- **Resource Encapsulation**: Moved all library strings and test tags to the core module.

### Changed
- **Documentation Migration**: All technical guides moved to `collections/docs/`.
- **Artifact Rebranding**: Primary artifact name is now **`collections`**.

## [0.2.8] - 2026-08-07
... (previous history)
