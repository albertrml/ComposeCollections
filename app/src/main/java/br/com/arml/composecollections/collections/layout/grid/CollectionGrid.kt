/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionAnimationMode
import br.com.arml.composecollections.collections.defaults.CollectionDefaults
import br.com.arml.composecollections.collections.defaults.CollectionDimensionDefaults
import br.com.arml.composecollections.collections.defaults.CollectionDimensions
import br.com.arml.composecollections.collections.defaults.CollectionIconDefaults
import br.com.arml.composecollections.collections.defaults.CollectionIcons
import br.com.arml.composecollections.collections.defaults.CollectionLayoutDefaults
import br.com.arml.composecollections.collections.defaults.CollectionLayoutSpec
import br.com.arml.composecollections.collections.defaults.CollectionLabelDefaults
import br.com.arml.composecollections.collections.defaults.CollectionLabels
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.defaults.CollectionTheme
import br.com.arml.composecollections.collections.defaults.LocalCollectionLabels
import br.com.arml.composecollections.collections.internal.CollectionLinearIndicator
import br.com.arml.composecollections.collections.layout.foundation.CollectionScaffold
import br.com.arml.composecollections.collections.layout.grid.scope.CollectionGridScope
import br.com.arml.composecollections.collections.layout.grid.scope.CollectionGridScopeImpl
import br.com.arml.composecollections.collections.layout.grid.scope.renderCollectionItems
import br.com.arml.composecollections.collections.state.CollectionState
import br.com.arml.composecollections.collections.state.rememberCollectionGridState

/**
 * A highly customizable grid container.
 */
@Composable
fun CollectionGrid(
    cells: GridCells,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    mode: CollectionMode = CollectionMode.Paged,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    collectionState: CollectionState = rememberCollectionGridState(gridState, mode, animationMode),
    layoutSpec: CollectionLayoutSpec = CollectionLayoutDefaults.Vertical,
    navigationAlignment: CollectionAlignment = CollectionAlignment.None,
    isOverlay: Boolean = false,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.ExpandLayout,
    labels: CollectionLabels = LocalCollectionLabels.current ?: CollectionLabelDefaults.defaultLabels(mode),
    icons: CollectionIcons = CollectionIconDefaults.default,
    dimens: CollectionDimensions = CollectionDimensionDefaults.default,
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    content: CollectionGridScope.() -> Unit
) {
    val gridScope = remember(content) { CollectionGridScopeImpl().apply(content) }
    val isHorizontal = layoutSpec is CollectionLayoutSpec.Horizontal

    val currentHeaderIndex by remember {
        derivedStateOf {
            gridScope.headerIndexes.lastOrNull { it <= gridState.firstVisibleItemIndex }
        }
    }

    CollectionScaffold(
        modifier = modifier.testTag(CollectionDefaults.ComponentTestTag),
        isOverlay = isOverlay,
        navigationAlignment = navigationAlignment,
        labels = labels,
        icons = icons,
        dimens = dimens,
        collectionState = collectionState,
        isHorizontal = isHorizontal,
        expandLayout = expandLayout,
        backwardControl = backwardControl,
        forwardControl = forwardControl,
        indicator = {
            if (showIndicator) {
                CollectionLinearIndicator(
                    progress = collectionState.scrollProgress,
                    isHorizontal = isHorizontal
                )
            }
        },
        topOverlay = {
            currentHeaderIndex?.let { index ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    gridScope.items[index].content(null)
                }
            }
        },
        container = { containerModifier ->
            val dimensions = CollectionTheme.dimensions
            when (layoutSpec) {
                is CollectionLayoutSpec.Vertical -> LazyVerticalGrid(
                    columns = cells,
                    modifier = containerModifier.fillMaxWidth(),
                    state = gridState,
                    verticalArrangement = layoutSpec.arrangement,
                    horizontalArrangement = Arrangement.spacedBy(dimensions.itemSpacing)
                ) {
                    renderCollectionItems(gridScope.items)
                }

                is CollectionLayoutSpec.Horizontal -> LazyHorizontalGrid(
                    rows = cells,
                    modifier = containerModifier.fillMaxWidth(),
                    state = gridState,
                    horizontalArrangement = layoutSpec.arrangement,
                    verticalArrangement = Arrangement.spacedBy(dimensions.itemSpacing)
                ) {
                    renderCollectionItems(gridScope.items)
                }
            }
        }
    )
}

/**
 * A specialized grid that scrolls page-by-page.
 */
@Composable
fun CollectionPagedGrid(
    cells: GridCells,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    layoutSpec: CollectionLayoutSpec = CollectionLayoutDefaults.Vertical,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    isOverlay: Boolean = false,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.ExpandLayout,
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    content: CollectionGridScope.() -> Unit
) = CollectionGrid(
    cells = cells,
    modifier = modifier,
    gridState = gridState,
    mode = CollectionMode.Paged,
    animationMode = animationMode,
    layoutSpec = layoutSpec,
    navigationAlignment = navigationAlignment,
    isOverlay = isOverlay,
    showIndicator = showIndicator,
    expandLayout = expandLayout,
    backwardControl = backwardControl,
    forwardControl = forwardControl,
    content = content
)

/**
 * A specialized grid that provides jump-to-extreme controls.
 */
@Composable
fun CollectionEdgedGrid(
    cells: GridCells,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    layoutSpec: CollectionLayoutSpec = CollectionLayoutDefaults.Vertical,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    isOverlay: Boolean = false,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.ExpandLayout,
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    content: CollectionGridScope.() -> Unit
) = CollectionGrid(
    cells = cells,
    modifier = modifier,
    gridState = gridState,
    mode = CollectionMode.Edged,
    animationMode = animationMode,
    layoutSpec = layoutSpec,
    navigationAlignment = navigationAlignment,
    isOverlay = isOverlay,
    showIndicator = showIndicator,
    expandLayout = expandLayout,
    backwardControl = backwardControl,
    forwardControl = forwardControl,
    content = content
)
