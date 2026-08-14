# Getting Started

Learn how to integrate `ComposeCollections` and build your first navigable collection in minutes.

## 1. Installation

Add the dependency to your `build.gradle.kts` (module app):

```kotlin
dependencies {
    implementation("br.com.arml.composecollections:composecollections:0.2.5")
}
```

## 2. Basic List Usage

Use `PagedList` to scroll by visible viewport "pages".

```kotlin
import br.com.arml.composecollections.scrollables.layout.list.PagedList
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutSpec
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment

@Composable
fun MyList() {
    PagedList(
        layoutSpec = QuickNavLayoutSpec.Vertical(),
        navigationAlignment = NavigationAlignment.Bottom
    ) {
        items(100) { index ->
            Text("Item $index", modifier = Modifier.padding(16.dp))
        }
    }
}
```

## 3. Basic Grid Usage

Use `EdgedGrid` to jump directly to the start or end of a grid.

```kotlin
import br.com.arml.composecollections.scrollables.layout.grid.EdgedGrid
import androidx.compose.foundation.lazy.grid.GridCells

@Composable
fun MyGrid() {
    EdgedGrid(
        cells = GridCells.Fixed(3),
        navigationAlignment = NavigationAlignment.End,
        isOverlay = true // Buttons float over the grid
    ) {
        items(100) { index ->
            Card { Text("Box $index") }
        }
    }
}
```

## 4. Staggered Grid (Pinterest Style)

For layouts with items of varying sizes, use `PagedStaggeredGrid`.

```kotlin
import br.com.arml.composecollections.scrollables.layout.grid.PagedStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells

@Composable
fun PinterestFeed() {
    PagedStaggeredGrid(
        cells = StaggeredGridCells.Fixed(2),
        navigationAlignment = NavigationAlignment.Bottom
    ) {
        items(100) { index ->
            // Irregular sizes will create the staggered effect
            Box(...) 
        }
    }
}
```

## Key Concepts

- **layoutSpec**: Controls orientation (Vertical/Horizontal), item spacing, and alignment.
- **navigationAlignment**: Controls where the navigation buttons appear (`Top`, `Bottom`, `Start`, `End`, `Horizontal`, `Vertical`).
- **isOverlay**: Toggle between traditional stacked layout and modern floating controls.
