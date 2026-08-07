# Customization Guide

`ComposeCollections` is built for flexibility. You can customize visual elements (Theme) or the underlying logic (State).

## 1. Theming

Use `QuickNavTheme` to globally or locally override labels, icons, and transitions.

```kotlin
val customLabels = QuickNavLabels(
    previousLabel = "Voltar",
    previousContentDescription = "Ir para página anterior",
    previousTag = "BackBtn",
    nextLabel = "Avançar",
    nextContentDescription = "Ir para próxima página",
    nextTag = "NextBtn"
)

QuickNavTheme(labels = customLabels) {
    // All lists inside this block will use Portuguese labels
    PagedList(...) { ... }
}
```

## 2. Custom Icons

You can swap the standard arrows for any [ImageVector].

```kotlin
QuickNavTheme(
    icons = QuickNavIcons(
        up = Icons.Default.ArrowCircleUp,
        down = Icons.Default.ArrowCircleDown,
        left = Icons.Default.ArrowCircleLeft,
        right = Icons.Default.ArrowCircleRight
    )
) {
    EdgedGrid(...) { ... }
}
```

## 3. Advanced: Custom Navigation Logic

The library uses the `QuickNavState` interface. You can implement this to create custom scroll behaviors (e.g., hiding buttons based on time or scroll speed).

```kotlin
class MyProState(val listState: LazyListState) : QuickNavState {
    // Show buttons only if we scrolled more than 5 items
    override val showScrollToStart get() = listState.firstVisibleItemIndex > 5
    
    // Custom scroll animation (Snap instead of Smooth)
    override fun animateScrollToStart(scope: CoroutineScope) = scope.launch {
        listState.scrollToItem(0)
    }

    // ... implement other members
}

// Usage
val state = rememberLazyListState()
PagedList(
    listState = state,
    quickNavState = remember { MyProState(state) }
) { ... }
```

## 4. Visibility Transitions

Change how buttons appear and disappear using `QuickNavTransitions`.

```kotlin
QuickNavTheme(
    transitions = QuickNavTransitions(
        enter = expandVertically(),
        exit = shrinkVertically()
    )
) {
    PagedList(...) { ... }
}
```
