/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionAnimationMode
import br.com.arml.composecollections.collections.defaults.CollectionDefaults
import br.com.arml.composecollections.collections.defaults.CollectionDimensionDefaults
import br.com.arml.composecollections.collections.defaults.CollectionDimensions
import br.com.arml.composecollections.collections.defaults.CollectionIconDefaults
import br.com.arml.composecollections.collections.defaults.CollectionIcons
import br.com.arml.composecollections.collections.defaults.CollectionLabelDefaults
import br.com.arml.composecollections.collections.defaults.CollectionLabels
import br.com.arml.composecollections.collections.defaults.CollectionLayoutDefaults
import br.com.arml.composecollections.collections.defaults.CollectionLayoutSpec
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.defaults.LocalCollectionLabels
import br.com.arml.composecollections.collections.internal.CollectionLinearIndicator
import br.com.arml.composecollections.collections.layout.foundation.CollectionScaffold
import br.com.arml.composecollections.collections.state.CollectionState
import br.com.arml.composecollections.collections.state.rememberCollectionListState

/**
 * A highly customizable list container.
 */
@Composable
fun CollectionList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    mode: CollectionMode = CollectionMode.Paged,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    collectionState: CollectionState = rememberCollectionListState(listState, mode, animationMode),
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
    content: LazyListScope.() -> Unit,
) {
    val isHorizontal = layoutSpec is CollectionLayoutSpec.Horizontal

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
    ) { containerModifier ->
        when (layoutSpec) {
            is CollectionLayoutSpec.Vertical -> LazyColumn(
                modifier = containerModifier.fillMaxWidth(),
                state = listState,
                verticalArrangement = layoutSpec.arrangement,
                horizontalAlignment = layoutSpec.alignment,
                content = content
            )

            is CollectionLayoutSpec.Horizontal -> LazyRow(
                modifier = containerModifier.fillMaxWidth(),
                state = listState,
                horizontalArrangement = layoutSpec.arrangement,
                verticalAlignment = layoutSpec.alignment,
                content = content
            )
        }
    }
}

/**
 * A specialized list that scrolls through content page-by-page.
 */
@Composable
fun CollectionPagedList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    layoutSpec: CollectionLayoutSpec = CollectionLayoutDefaults.Vertical,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    isOverlay: Boolean = false,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.ExpandLayout,
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    content: LazyListScope.() -> Unit
) = CollectionList(
    modifier = modifier,
    listState = listState,
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
 * A specialized list that provides controls to jump directly to extremes.
 */
@Composable
fun CollectionEdgedList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    layoutSpec: CollectionLayoutSpec = CollectionLayoutDefaults.Vertical,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    isOverlay: Boolean = false,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.ExpandLayout,
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    content: LazyListScope.() -> Unit
) = CollectionList(
    modifier = modifier,
    listState = listState,
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
