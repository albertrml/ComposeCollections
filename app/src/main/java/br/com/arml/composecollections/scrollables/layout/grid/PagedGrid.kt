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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.R
import br.com.arml.composecollections.scrollables.defaults.LocalQuickNavLabels
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment
import br.com.arml.composecollections.scrollables.defaults.QuickNavIconDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavIcons
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutSpec
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabelDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabels
import br.com.arml.composecollections.scrollables.layout.foundation.QuickNavScaffold
import br.com.arml.composecollections.scrollables.state.QuickNavState
import br.com.arml.composecollections.scrollables.state.rememberQuickNavGridState

/**
 * A highly customizable grid that navigates through content page-by-page.
 *
 * This component supports both [LazyVerticalGrid] and [LazyHorizontalGrid] through
 * the [layoutSpec] parameter. It uses current viewport measurements to define the
 * scroll distance for pagination.
 *
 * @param cells The cell configuration for the grid (Fixed or Adaptive).
 * @param modifier The modifier to be applied to the root layout.
 * @param gridState The state object to be used to control the grid.
 * @param quickNavState The navigation state controller. Defaults to a standard grid implementation.
 * @param layoutSpec Defines the orientation and item arrangement.
 * @param navigationAlignment Where to place the navigation controls.
 * @param isOverlay If true, navigation buttons float over the grid content.
 * @param labels Labels and tags for navigation buttons. Defaults to themed or paged defaults.
 * @param icons Icon set for navigation buttons. Defaults to standard theme icons.
 * @param content The content of the grid, defined using [LazyGridScope].
 */
@Composable
fun PagedGrid(
    cells: GridCells,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    quickNavState: QuickNavState = rememberQuickNavGridState(gridState),
    layoutSpec: QuickNavLayoutSpec = QuickNavLayoutDefaults.Vertical,
    navigationAlignment: NavigationAlignment = NavigationAlignment.Bottom,
    isOverlay: Boolean = false,
    labels: QuickNavLabels = LocalQuickNavLabels.current ?: QuickNavLabelDefaults.pagedLabels(),
    icons: QuickNavIcons = QuickNavIconDefaults.default,
    content: LazyGridScope.() -> Unit
) {
    val scope = rememberCoroutineScope()

    // Stable actions
    val onScrollBack = remember(quickNavState, scope) { { quickNavState.animateScrollToPreviousPage(scope); Unit } }
    val onScrollForward = remember(quickNavState, scope) { { quickNavState.animateScrollToNextPage(scope); Unit } }

    val isHorizontal = layoutSpec is QuickNavLayoutSpec.Horizontal

    QuickNavScaffold(
        modifier = modifier.testTag(stringResource(R.string.pagedQuickNavList_component_testTag)),
        isOverlay = isOverlay,
        navigationAlignment = navigationAlignment,
        labels = labels,
        icons = icons,
        isHorizontal = isHorizontal,
        showBackward = { quickNavState.showScrollToStart },
        showForward = { quickNavState.showScrollToEnd },
        onScrollBack = onScrollBack,
        onScrollForward = onScrollForward
    ) { containerModifier ->
        when (layoutSpec) {
            is QuickNavLayoutSpec.Vertical -> LazyVerticalGrid(
                columns = cells,
                modifier = containerModifier.fillMaxWidth(),
                state = gridState,
                verticalArrangement = layoutSpec.arrangement,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                content = content
            )
            is QuickNavLayoutSpec.Horizontal -> LazyHorizontalGrid(
                rows = cells,
                modifier = containerModifier.fillMaxWidth(),
                state = gridState,
                horizontalArrangement = layoutSpec.arrangement,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content = content
            )
        }
    }
}
