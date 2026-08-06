/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.layout.foundation

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import br.com.arml.composecollections.R
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment
import br.com.arml.composecollections.scrollables.defaults.QuickNavDimensions
import br.com.arml.composecollections.scrollables.defaults.QuickNavIcons
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabels
import br.com.arml.composecollections.scrollables.defaults.QuickNavTheme
import br.com.arml.composecollections.scrollables.internal.NavigationRouter
import br.com.arml.composecollections.scrollables.state.QuickNavState
import kotlinx.coroutines.CoroutineScope

/**
 * High-level template that unifies theme, scaffold layout, and navigation routing.
 *
 * This component acts as the structural foundation for specialized lists and grids.
 * It manages the propagation of theme settings and uses a [NavigationRouter] to
 * correctly place navigation panels based on the provided [navigationAlignment].
 *
 * @param modifier The modifier to be applied to the root layout.
 * @param isOverlay If true, navigation controls float over the [container] content.
 * @param navigationAlignment Where to place the navigation controls (Top, Bottom, End, etc.).
 * @param labels Labels and tags for navigation buttons.
 * @param icons Icon set for navigation buttons.
 * @param dimens Dimension tokens for spacing and sizing.
 * @param quickNavState The state controller for navigation actions and visibility.
 * @param isHorizontal The scroll orientation of the inner content.
 * @param indicator Optional slot for displaying scroll progress.
 * @param topOverlay Optional slot for floating content at the top (e.g., sticky headers for Grid).
 * @param container The main scrollable UI component (e.g., LazyColumn, LazyRow).
 */
@Composable
fun QuickNavScaffold(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = false,
    navigationAlignment: NavigationAlignment = NavigationAlignment.Bottom,
    labels: QuickNavLabels,
    icons: QuickNavIcons,
    dimens: QuickNavDimensions,
    quickNavState: QuickNavState,
    isHorizontal: Boolean,
    indicator: @Composable () -> Unit = {},
    topOverlay: @Composable () -> Unit = {},
    container: @Composable (Modifier) -> Unit,
) {
    val scope = rememberCoroutineScope()
    
    QuickNavTheme(labels = labels, icons = icons, dimensions = dimens) {
        val dimensions = QuickNavTheme.dimensions
        val keyboardModifier = Modifier
            .onPreviewKeyEvent { event ->
                if (event.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false
                
                when (event.key) {
                    Key.PageUp -> { quickNavState.animateScrollToBackward(scope); true }
                    Key.PageDown -> { quickNavState.animateScrollToForward(scope); true }
                    Key.MoveHome -> { quickNavState.animateScrollToStart(scope); true }
                    Key.MoveEnd -> { quickNavState.animateScrollToEnd(scope); true }
                    else -> false
                }
            }
            .focusable()
            .testTag(stringResource(R.string.quickNav_scaffold_keyboard_testTag))

        if(isHorizontal){
            Column(
                modifier = modifier.then(keyboardModifier),
                verticalArrangement = Arrangement.spacedBy(dimensions.panelToContentSpacing)
            ) {
                indicator()
                Box(Modifier.weight(1f)) {
                    QuickNavNavigationFrame(
                        modifier = Modifier.fillMaxWidth(),
                        isOverlay = isOverlay,
                        navigationAlignment = navigationAlignment,
                        quickNavState = quickNavState,
                        scope = scope,
                        isHorizontal = true,
                        container = container
                    )
                    Box(Modifier.testTag(stringResource(R.string.quickNav_scaffold_header_overlay_testTag))) {
                        topOverlay()
                    }
                }
            }
        }
        else {
            Row(
                modifier = modifier.then(keyboardModifier),
                horizontalArrangement = Arrangement.spacedBy(dimensions.panelToContentSpacing)
            ) {
                Box(Modifier.weight(1f)) {
                    QuickNavNavigationFrame(
                        modifier = Modifier.fillMaxWidth(),
                        isOverlay = isOverlay,
                        navigationAlignment = navigationAlignment,
                        quickNavState = quickNavState,
                        scope = scope,
                        isHorizontal = false,
                        container = container
                    )
                    Box(Modifier.testTag(stringResource(R.string.quickNav_scaffold_header_overlay_testTag))) {
                        topOverlay()
                    }
                }
                indicator()
            }
        }
    }
}

/**
 * Internal frame that assembles navigation routers around the main content.
 */
@Composable
internal fun QuickNavNavigationFrame(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = false,
    navigationAlignment: NavigationAlignment = NavigationAlignment.Bottom,
    quickNavState: QuickNavState,
    scope: CoroutineScope,
    isHorizontal: Boolean,
    container: @Composable (Modifier) -> Unit
) {
    QuickNavLayout(
        modifier = modifier,
        isOverlay = isOverlay,
        contentTop = {
            NavigationRouter(
                alignment = navigationAlignment,
                target = NavigationAlignment.Top,
                secondaryTarget = NavigationAlignment.Vertical,
                quickNavState = quickNavState,
                scope = scope,
                isHorizontal = isHorizontal,
                isStart = true
            )
        },
        contentBottom = {
            NavigationRouter(
                alignment = navigationAlignment,
                target = NavigationAlignment.Bottom,
                secondaryTarget = NavigationAlignment.Vertical,
                quickNavState = quickNavState,
                scope = scope,
                isHorizontal = isHorizontal,
                isStart = false
            )
        },
        contentLeft = {
            NavigationRouter(
                alignment = navigationAlignment,
                target = NavigationAlignment.Start,
                secondaryTarget = NavigationAlignment.Horizontal,
                quickNavState = quickNavState,
                scope = scope,
                isHorizontal = isHorizontal,
                isStart = true
            )
        },
        contentRight = {
            NavigationRouter(
                alignment = navigationAlignment,
                target = NavigationAlignment.End,
                secondaryTarget = NavigationAlignment.Horizontal,
                quickNavState = quickNavState,
                scope = scope,
                isHorizontal = isHorizontal,
                isStart = false
            )
        }
    ) {
        container(Modifier)
    }
}
