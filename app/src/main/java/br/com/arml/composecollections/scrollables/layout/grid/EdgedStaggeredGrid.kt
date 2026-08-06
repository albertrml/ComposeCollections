/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.layout.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import br.com.arml.composecollections.R
import br.com.arml.composecollections.scrollables.defaults.LocalQuickNavLabels
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment
import br.com.arml.composecollections.scrollables.defaults.QuickNavAnimationMode
import br.com.arml.composecollections.scrollables.defaults.QuickNavDimensionDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavDimensions
import br.com.arml.composecollections.scrollables.defaults.QuickNavIconDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavIcons
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutSpec
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabelDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabels
import br.com.arml.composecollections.scrollables.defaults.QuickNavMode
import br.com.arml.composecollections.scrollables.defaults.QuickNavTheme
import br.com.arml.composecollections.scrollables.internal.QuickNavLinearIndicator
import br.com.arml.composecollections.scrollables.layout.foundation.QuickNavScaffold
import br.com.arml.composecollections.scrollables.layout.grid.scope.QuickNavStaggeredGridScope
import br.com.arml.composecollections.scrollables.layout.grid.scope.QuickNavStaggeredGridScopeImpl
import br.com.arml.composecollections.scrollables.layout.grid.scope.renderQuickNavItems
import br.com.arml.composecollections.scrollables.state.QuickNavState
import br.com.arml.composecollections.scrollables.state.rememberQuickNavStaggeredGridState

/**
 * A highly customizable staggered grid that provides navigation controls to jump directly to the
 * start or end of the collection.
 *
 * @param modifier The modifier to be applied to the root layout.
 * @param gridState The state object to be used to control the grid.
 * @param quickNavState The navigation state controller. Defaults to a standard staggered implementation.
 * @param layoutSpec Defines the orientation and item arrangement.
 * @param navigationAlignment Where to place the navigation controls.
 * @param animationMode The scroll animation preset.
 * @param cells The cell configuration for the staggered grid.
 * @param isOverlay If true, navigation buttons float over the grid content.
 * @param showIndicator If true, displays a scroll progress indicator.
 * @param labels Labels and tags for navigation buttons. Defaults to themed or edged defaults.
 * @param icons Icon set for navigation buttons. Defaults to standard theme icons.
 * @param dimens Dimension tokens for spacing and sizing.
 * @param content The content of the staggered grid, defined using [br.com.arml.composecollections.scrollables.layout.grid.scope.QuickNavStaggeredGridScope].
 */
@Composable
fun EdgedStaggeredGrid(
    modifier: Modifier = Modifier,
    gridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    quickNavState: QuickNavState = rememberQuickNavStaggeredGridState(gridState, QuickNavMode.Edged),
    layoutSpec: QuickNavLayoutSpec = QuickNavLayoutDefaults.Vertical,
    navigationAlignment: NavigationAlignment = NavigationAlignment.Bottom,
    animationMode: QuickNavAnimationMode = QuickNavAnimationMode.Default,
    cells: StaggeredGridCells,
    isOverlay: Boolean = false,
    showIndicator: Boolean = false,
    labels: QuickNavLabels = LocalQuickNavLabels.current ?: QuickNavLabelDefaults.edgedLabels(),
    icons: QuickNavIcons = QuickNavIconDefaults.default,
    dimens: QuickNavDimensions = QuickNavDimensionDefaults.default,
    content: QuickNavStaggeredGridScope.() -> Unit
) {
    val gridScope = remember(content) { QuickNavStaggeredGridScopeImpl().apply(content) }
    val isHorizontal = layoutSpec is QuickNavLayoutSpec.Horizontal

    val currentHeaderIndex by remember {
        derivedStateOf {
            gridScope.headerIndexes.lastOrNull { it <= gridState.firstVisibleItemIndex }
        }
    }

    QuickNavScaffold(
        modifier = modifier.testTag(stringResource(R.string.quickNavList_component_testTag)),
        isOverlay = isOverlay,
        navigationAlignment = navigationAlignment,
        labels = labels,
        icons = icons,
        dimens = dimens,
        quickNavState = quickNavState,
        isHorizontal = isHorizontal,
        indicator = {
            if (showIndicator) {
                QuickNavLinearIndicator(
                    progress = quickNavState.scrollProgress,
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
            val dimensions = QuickNavTheme.dimensions
            when (layoutSpec) {
                is QuickNavLayoutSpec.Vertical -> LazyVerticalStaggeredGrid(
                    columns = cells,
                    modifier = containerModifier.fillMaxWidth(),
                    state = gridState,
                    verticalItemSpacing = dimensions.itemSpacing,
                    horizontalArrangement = Arrangement.spacedBy(dimensions.itemSpacing)
                ) {
                    renderQuickNavItems(gridScope.items)
                }

                is QuickNavLayoutSpec.Horizontal -> LazyHorizontalStaggeredGrid(
                    rows = cells,
                    modifier = containerModifier.fillMaxWidth(),
                    state = gridState,
                    verticalArrangement = Arrangement.spacedBy(dimensions.itemSpacing),
                    horizontalItemSpacing = dimensions.itemSpacing
                ) {
                    renderQuickNavItems(gridScope.items)
                }
            }
        },
    )
}
