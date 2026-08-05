/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.layout.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import br.com.arml.composecollections.R
import br.com.arml.composecollections.scrollables.defaults.LocalQuickNavLabels
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment
import br.com.arml.composecollections.scrollables.defaults.QuickNavAnimationMode
import br.com.arml.composecollections.scrollables.defaults.QuickNavIconDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavIcons
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabelDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabels
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutSpec
import br.com.arml.composecollections.scrollables.defaults.QuickNavMode
import br.com.arml.composecollections.scrollables.internal.QuickNavLinearIndicator
import br.com.arml.composecollections.scrollables.layout.foundation.QuickNavScaffold
import br.com.arml.composecollections.scrollables.state.QuickNavState
import br.com.arml.composecollections.scrollables.state.rememberQuickNavListState

/**
 * A highly customizable list that navigates through content page-by-page.
 *
 * @param modifier The modifier to be applied to the root layout.
 * @param listState The state object to be used to control the list.
 * @param quickNavState The navigation state controller.
 * @param layoutSpec Defines the orientation, arrangement, and alignment of the list items.
 * @param navigationAlignment Where to place the navigation controls.
 * @param animationMode The scroll animation preset.
 * @param isOverlay If true, navigation buttons float over the list content.
 * @param showIndicator If true, displays a scroll progress indicator.
 * @param labels Labels and tags for navigation buttons.
 * @param icons Icon set for navigation buttons.
 * @param content The content of the list.
 */
@Composable
fun PagedList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    quickNavState: QuickNavState = rememberQuickNavListState(listState, QuickNavMode.Paged),
    layoutSpec: QuickNavLayoutSpec = QuickNavLayoutDefaults.Vertical,
    navigationAlignment: NavigationAlignment = NavigationAlignment.Bottom,
    animationMode: QuickNavAnimationMode = QuickNavAnimationMode.Default,
    isOverlay: Boolean = false,
    showIndicator: Boolean = false,
    labels: QuickNavLabels = LocalQuickNavLabels.current ?: QuickNavLabelDefaults.pagedLabels(),
    icons: QuickNavIcons = QuickNavIconDefaults.default,
    content: LazyListScope.() -> Unit
) {
    val scope = rememberCoroutineScope()

    // Stable actions
    val onScrollBackward = remember(quickNavState, scope) { { quickNavState.animateScrollToBackward(scope); Unit } }
    val onScrollForward = remember(quickNavState, scope) { { quickNavState.animateScrollToForward(scope); Unit } }
    val onScrollToStart = remember(quickNavState, scope) { { quickNavState.animateScrollToStart(scope); Unit } }
    val onScrollToEnd = remember(quickNavState, scope) { { quickNavState.animateScrollToEnd(scope); Unit } }

    val isHorizontal = layoutSpec is QuickNavLayoutSpec.Horizontal

    QuickNavScaffold(
        modifier = modifier.testTag(stringResource(R.string.quickNavList_component_testTag)),
        isOverlay = isOverlay,
        navigationAlignment = navigationAlignment,
        labels = labels,
        icons = icons,
        isHorizontal = isHorizontal,
        showBackward = { quickNavState.showScrollToBackward },
        showForward = { quickNavState.showScrollToForward },
        onScrollBackward = onScrollBackward,
        onScrollForward = onScrollForward,
        onScrollToStart = onScrollToStart,
        onScrollToEnd = onScrollToEnd,
        indicator = {
            if (showIndicator) {
                QuickNavLinearIndicator(
                    progress = quickNavState.scrollProgress,
                    isHorizontal = isHorizontal
                )
            }
        }
    ) { containerModifier ->
        when (layoutSpec) {
            is QuickNavLayoutSpec.Vertical -> LazyColumn(
                modifier = containerModifier.fillMaxWidth(),
                state = listState,
                verticalArrangement = layoutSpec.arrangement,
                horizontalAlignment = layoutSpec.alignment,
                content = content
            )
            is QuickNavLayoutSpec.Horizontal -> LazyRow(
                modifier = containerModifier.fillMaxWidth(),
                state = listState,
                horizontalArrangement = layoutSpec.arrangement,
                verticalAlignment = layoutSpec.alignment,
                content = content
            )
        }
    }
}
