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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.R
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment
import br.com.arml.composecollections.scrollables.defaults.QuickNavIcons
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabels
import br.com.arml.composecollections.scrollables.defaults.QuickNavTheme
import br.com.arml.composecollections.scrollables.internal.NavigationRouter

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
 * @param isHorizontal The scroll orientation of the inner content.
 * @param showBackward Lambda returning true if the backward/up button should be shown.
 * @param showForward Lambda returning true if the forward/down button should be shown.
 * @param onScrollBackward Callback for the backward navigation action.
 * @param onScrollForward Callback for the forward navigation action.
 * @param onScrollToStart Optional callback for jumping to the absolute start (Home key).
 * @param onScrollToEnd Optional callback for jumping to the absolute end (End key).
 * @param indicator Optional slot for displaying scroll progress.
 * @param container The main scrollable UI component (e.g., LazyColumn, LazyRow).
 */
@Composable
fun QuickNavScaffold(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = false,
    navigationAlignment: NavigationAlignment = NavigationAlignment.Bottom,
    labels: QuickNavLabels,
    icons: QuickNavIcons,
    isHorizontal: Boolean,
    showBackward: () -> Boolean,
    showForward: () -> Boolean,
    onScrollBackward: () -> Unit,
    onScrollForward: () -> Unit,
    onScrollToStart: () -> Unit = {},
    onScrollToEnd: () -> Unit = {},
    indicator: @Composable () -> Unit = {},
    container: @Composable (Modifier) -> Unit
) {
    QuickNavTheme(labels = labels, icons = icons) {
        val keyboardModifier = Modifier
            .onKeyEvent { event ->
                when (event.key) {
                    Key.PageUp -> { onScrollBackward(); true }
                    Key.PageDown -> { onScrollForward(); true }
                    Key.MoveHome -> { onScrollToStart(); true }
                    Key.MoveEnd -> { onScrollToEnd(); true }
                    else -> false
                }
            }
            .focusable()
            .testTag(stringResource(R.string.quickNav_scaffold_keyboard_testTag))

        if(isHorizontal){
            Column(
                modifier = modifier.then(keyboardModifier),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                indicator()
                QuickNavNavigationFrame(
                    modifier = Modifier,
                    isOverlay = isOverlay,
                    navigationAlignment = navigationAlignment,
                    labels = labels,
                    icons = icons,
                    isHorizontal = true,
                    showBackward = showBackward,
                    showForward = showForward,
                    onScrollBackward = onScrollBackward,
                    onScrollForward = onScrollForward,
                    container = container
                )
            }
        }
        else {
            Row(
                modifier = modifier.then(keyboardModifier),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                QuickNavNavigationFrame(
                    modifier = Modifier.weight(1f),
                    isOverlay = isOverlay,
                    navigationAlignment = navigationAlignment,
                    labels = labels,
                    icons = icons,
                    isHorizontal = false,
                    showBackward = { showBackward() },
                    showForward = { showForward() },
                    onScrollBackward = onScrollBackward,
                    onScrollForward = onScrollForward,
                    container = container
                )
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
    labels: QuickNavLabels,
    icons: QuickNavIcons,
    isHorizontal: Boolean,
    showBackward: () -> Boolean,
    showForward: () -> Boolean,
    onScrollBackward: () -> Unit,
    onScrollForward: () -> Unit,
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
                isHorizontal = isHorizontal,
                isStart = true,
                labels = labels,
                icons = icons,
                showBackward = showBackward,
                showForward = showForward,
                onScrollBackward = onScrollBackward,
                onScrollForward = onScrollForward
            )
        },
        contentBottom = {
            NavigationRouter(
                alignment = navigationAlignment,
                target = NavigationAlignment.Bottom,
                secondaryTarget = NavigationAlignment.Vertical,
                isHorizontal = isHorizontal,
                isStart = false,
                labels = labels,
                icons = icons,
                showBackward = showBackward,
                showForward = showForward,
                onScrollBackward = onScrollBackward,
                onScrollForward = onScrollForward
            )
        },
        contentLeft = {
            NavigationRouter(
                alignment = navigationAlignment,
                target = NavigationAlignment.Start,
                secondaryTarget = NavigationAlignment.Horizontal,
                isHorizontal = isHorizontal,
                isStart = true,
                labels = labels,
                icons = icons,
                showBackward = showBackward,
                showForward = showForward,
                onScrollBackward = onScrollBackward,
                onScrollForward = onScrollForward
            )
        },
        contentRight = {
            NavigationRouter(
                alignment = navigationAlignment,
                target = NavigationAlignment.End,
                secondaryTarget = NavigationAlignment.Horizontal,
                isHorizontal = isHorizontal,
                isStart = false,
                labels = labels,
                icons = icons,
                showBackward = showBackward,
                showForward = showForward,
                onScrollBackward = onScrollBackward,
                onScrollForward = onScrollForward
            )
        }
    ) {
        container(Modifier)
    }
}
