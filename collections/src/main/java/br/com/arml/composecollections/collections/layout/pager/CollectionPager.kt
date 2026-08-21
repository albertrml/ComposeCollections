/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout.pager

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.collections.components.indicators.CollectionDotIndicator
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionAnimationMode
import br.com.arml.composecollections.collections.defaults.CollectionDefaults
import br.com.arml.composecollections.collections.defaults.CollectionLabelDefaults
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.defaults.CollectionUIState
import br.com.arml.composecollections.collections.defaults.rememberCollectionUIState
import br.com.arml.composecollections.collections.layout.foundation.CollectionScaffold
import br.com.arml.composecollections.collections.state.CollectionPagerState
import br.com.arml.composecollections.collections.state.CollectionState
import br.com.arml.composecollections.collections.state.rememberCollectionPagerState

/**
 * A highly customizable Pager container.
 */
@Composable
fun CollectionPager(
    state: CollectionPagerState,
    modifier: Modifier = Modifier,
    isHorizontal: Boolean = true,
    navigationAlignment: CollectionAlignment = CollectionAlignment.None,
    isOverlay: Boolean = true,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.expandLayout,
    uiState: CollectionUIState = rememberCollectionUIState(state.mode),
    indicator: @Composable BoxScope.(CollectionPagerState) -> Unit = {
        if (showIndicator) {
            CollectionDotIndicator(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(end = 16.dp),
                currentPage = state.currentPage,
                totalPages = state.totalPages
            )
        }
    },
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    pageContent: @Composable PagerScope.(Int) -> Unit,
) {
    CollectionScaffold(
        modifier = modifier.testTag(uiState.labels.componentTag),
        isOverlay = isOverlay,
        navigationAlignment = navigationAlignment,
        labels = uiState.labels,
        icons = uiState.icons,
        dimens = uiState.dimensions,
        collectionState = state,
        isHorizontal = isHorizontal,
        expandLayout = expandLayout,
        backwardControl = backwardControl,
        forwardControl = forwardControl,
        indicator = { indicator(state) },
    ) { containerModifier ->
        if (isHorizontal) {
            HorizontalPager(
                state = state.pagerState,
                modifier = containerModifier.fillMaxSize(),
                pageContent = pageContent
            )
        } else {
            VerticalPager(
                state = state.pagerState,
                modifier = containerModifier.fillMaxSize(),
                pageContent = pageContent
            )
        }
    }
}

/**
 * A specialized Pager that provides controls to jump directly to extremes.
 */
@Composable
fun CollectionEdgedPager(
    pageCount: () -> Int,
    modifier: Modifier = Modifier,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    isHorizontal: Boolean = true,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    isOverlay: Boolean = true,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.expandLayout,
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    pageContent: @Composable PagerScope.(Int) -> Unit,
) {
    val state = rememberCollectionPagerState(
        pageCount = pageCount,
        mode = CollectionMode.Edged,
        animationMode = animationMode
    )

    CollectionPager(
        state = state,
        modifier = modifier,
        isHorizontal = isHorizontal,
        navigationAlignment = navigationAlignment,
        isOverlay = isOverlay,
        expandLayout = expandLayout,
        showIndicator = showIndicator,
        backwardControl = backwardControl,
        forwardControl = forwardControl,
        pageContent = pageContent
    )
}

/**
 * A specialized Pager that scrolls page-by-page.
 */
@Composable
fun CollectionPagedPager(
    pageCount: () -> Int,
    modifier: Modifier = Modifier,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    isHorizontal: Boolean = true,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    isOverlay: Boolean = true,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.expandLayout,
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    pageContent: @Composable PagerScope.(Int) -> Unit,
) {
    val state = rememberCollectionPagerState(
        pageCount = pageCount,
        mode = CollectionMode.Paged,
        animationMode = animationMode
    )

    CollectionPager(
        state = state,
        modifier = modifier,
        isHorizontal = isHorizontal,
        navigationAlignment = navigationAlignment,
        isOverlay = isOverlay,
        expandLayout = expandLayout,
        showIndicator = showIndicator,
        backwardControl = backwardControl,
        forwardControl = forwardControl,
        pageContent = pageContent
    )
}

/**
 * A specialized Pager that scrolls by a fixed number of pages.
 *
 * @param step The number of pages to jump on each navigation action.
 */
@Composable
fun CollectionSteppedPager(
    pageCount: () -> Int,
    step: Int,
    modifier: Modifier = Modifier,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    isHorizontal: Boolean = true,
    navigationAlignment: CollectionAlignment = CollectionAlignment.Bottom,
    isOverlay: Boolean = true,
    showIndicator: Boolean = false,
    expandLayout: Boolean = CollectionDefaults.expandLayout,
    backwardControl: @Composable ((CollectionState) -> Unit)? = null,
    forwardControl: @Composable ((CollectionState) -> Unit)? = null,
    pageContent: @Composable PagerScope.(Int) -> Unit,
) {
    val state = rememberCollectionPagerState(
        pageCount = pageCount,
        mode = CollectionMode.Stepped,
        animationMode = animationMode,
        step = step
    )

    val uiState = rememberCollectionUIState(
        mode = state.mode,
        labels = CollectionLabelDefaults.steppedLabels(step)
    )

    CollectionPager(
        state = state,
        modifier = modifier,
        isHorizontal = isHorizontal,
        navigationAlignment = navigationAlignment,
        uiState = uiState,
        isOverlay = isOverlay,
        expandLayout = expandLayout,
        showIndicator = showIndicator,
        backwardControl = backwardControl,
        forwardControl = forwardControl,
        pageContent = pageContent
    )
}
