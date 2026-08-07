/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.arml.composecollections.collections.defaults.CollectionTheme
import br.com.arml.composecollections.collections.state.CollectionState

/**
 * Defines the navigation direction for a [CollectionScrollButton].
 */
enum class ScrollDirection {
    Backward,
    Forward
}

/**
 * A specialized button that automatically handles visibility and scroll logic
 * based on the provided [CollectionState].
 *
 * @param state The [CollectionState] to monitor and control.
 * @param direction Whether this button scrolls backward or forward.
 * @param text The label text for the button.
 * @param icon The icon to display.
 * @param modifier The modifier for the button.
 * @param contentDescription Accessibility text.
 */
@Composable
fun CollectionScrollButton(
    state: CollectionState,
    direction: ScrollDirection,
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val scope = rememberCoroutineScope()
    val isVisible = if (direction == ScrollDirection.Backward) state.showScrollToBackward else state.showScrollToForward
    val transitions = CollectionTheme.transitions

    AnimatedVisibility(
        visible = isVisible,
        enter = transitions.enter,
        exit = transitions.exit
    ) {
        CollectionButton(
            modifier = modifier,
            text = text,
            icon = icon,
            iconContentDescription = contentDescription,
            onClick = {
                if (direction == ScrollDirection.Backward) {
                    state.animateScrollToBackward(scope)
                } else {
                    state.animateScrollToForward(scope)
                }
            }
        )
    }
}
