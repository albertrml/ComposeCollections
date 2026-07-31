/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.internal

import androidx.compose.runtime.Composable
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment
import br.com.arml.composecollections.scrollables.defaults.QuickNavIcons
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabels

/**
 * Internal component that routes the appropriate navigation panel based on alignment.
 *
 * This component is for internal library use only.
 */
@Composable
internal fun NavigationRouter(
    alignment: NavigationAlignment,
    target: NavigationAlignment,
    secondaryTarget: NavigationAlignment,
    isHorizontal: Boolean,
    isStart: Boolean,
    labels: QuickNavLabels,
    icons: QuickNavIcons,
    showBackward: () -> Boolean,
    showForward: () -> Boolean,
    onScrollBack: () -> Unit,
    onScrollForward: () -> Unit
) {
    when (alignment) {
        target -> {
            val useVerticalPanel = target == NavigationAlignment.Start || target == NavigationAlignment.End
            
            if (useVerticalPanel) {
                VerticalPanelNavigation(
                    isHorizontal = isHorizontal,
                    showScrollToBackward = showBackward,
                    showScrollToForward = showForward,
                    onScrollToBackward = onScrollBack,
                    onScrollToForward = onScrollForward
                )
            } else {
                HorizontalPanelNavigation(
                    isHorizontal = isHorizontal,
                    showScrollToBackward = showBackward,
                    showScrollToForward = showForward,
                    onScrollToBackward = onScrollBack,
                    onScrollToForward = onScrollForward
                )
            }
        }
        secondaryTarget -> {
            SinglePanelNavigation(
                label = if (isStart) labels.previousLabel else labels.nextLabel,
                icon = resolveNavigationIcon(icons, isStart, isHorizontal),
                contentDescription = if (isStart) labels.previousContentDescription else labels.nextContentDescription,
                testTag = if (isStart) labels.previousTag else labels.nextTag,
                showScrollButton = if (isStart) showBackward else showForward,
                onScrollTo = if (isStart) onScrollBack else onScrollForward
            )
        }
        else -> {}
    }
}
