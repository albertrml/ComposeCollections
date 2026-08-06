/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout.foundation

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
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionDimensions
import br.com.arml.composecollections.collections.defaults.CollectionIcons
import br.com.arml.composecollections.collections.defaults.CollectionLabels
import br.com.arml.composecollections.collections.defaults.CollectionTheme
import br.com.arml.composecollections.collections.internal.CollectionRouter
import br.com.arml.composecollections.collections.state.CollectionState
import kotlinx.coroutines.CoroutineScope

/**
 * High-level template that unifies theme, scaffold layout, and navigation routing.
 */
@Composable
fun CollectionScaffold(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = false,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    labels: CollectionLabels,
    icons: CollectionIcons,
    dimens: CollectionDimensions,
    collectionState: CollectionState,
    isHorizontal: Boolean,
    indicator: @Composable () -> Unit = {},
    topOverlay: @Composable () -> Unit = {},
    container: @Composable (Modifier) -> Unit,
) {
    val scope = rememberCoroutineScope()
    
    CollectionTheme(labels = labels, icons = icons, dimensions = dimens) {
        val dimensions = CollectionTheme.dimensions
        val keyboardModifier = Modifier
            .onPreviewKeyEvent { event ->
                if (event.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false
                
                when (event.key) {
                    Key.PageUp -> { collectionState.animateScrollToBackward(scope); true }
                    Key.PageDown -> { collectionState.animateScrollToForward(scope); true }
                    Key.MoveHome -> { collectionState.animateScrollToStart(scope); true }
                    Key.MoveEnd -> { collectionState.animateScrollToEnd(scope); true }
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
                Box(Modifier.weight(1f, fill = false)) {
                    CollectionNavigationFrame(
                        modifier = Modifier.fillMaxWidth(),
                        isOverlay = isOverlay,
                        navigationAlignment = navigationAlignment,
                        collectionState = collectionState,
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
                Box(Modifier.weight(1f, fill = false)) {
                    CollectionNavigationFrame(
                        modifier = Modifier.fillMaxWidth(),
                        isOverlay = isOverlay,
                        navigationAlignment = navigationAlignment,
                        collectionState = collectionState,
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
internal fun CollectionNavigationFrame(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = false,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    collectionState: CollectionState,
    scope: CoroutineScope,
    isHorizontal: Boolean,
    container: @Composable (Modifier) -> Unit
) {
    CollectionLayout(
        modifier = modifier,
        isOverlay = isOverlay,
        contentTop = {
            CollectionRouter(
                alignment = navigationAlignment,
                target = CollectionAlignment.Top,
                secondaryTarget = CollectionAlignment.Vertical,
                collectionState = collectionState,
                scope = scope,
                isHorizontal = isHorizontal,
                isStart = true
            )
        },
        contentBottom = {
            CollectionRouter(
                alignment = navigationAlignment,
                target = CollectionAlignment.Bottom,
                secondaryTarget = CollectionAlignment.Vertical,
                collectionState = collectionState,
                scope = scope,
                isHorizontal = isHorizontal,
                isStart = false
            )
        },
        contentLeft = {
            CollectionRouter(
                alignment = navigationAlignment,
                target = CollectionAlignment.Start,
                secondaryTarget = CollectionAlignment.Horizontal,
                collectionState = collectionState,
                scope = scope,
                isHorizontal = isHorizontal,
                isStart = true
            )
        },
        contentRight = {
            CollectionRouter(
                alignment = navigationAlignment,
                target = CollectionAlignment.End,
                secondaryTarget = CollectionAlignment.Horizontal,
                collectionState = collectionState,
                scope = scope,
                isHorizontal = isHorizontal,
                isStart = false
            )
        }
    ) {
        container(Modifier)
    }
}
