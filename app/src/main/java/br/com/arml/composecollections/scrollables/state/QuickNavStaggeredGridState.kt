/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.state

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Default implementation of [QuickNavState] for LazyStaggeredGrids.
 *
 * This class tracks the current scroll position and determines if navigation buttons
 * should be shown based on the visibility of the first and last items.
 *
 * @param gridState The [LazyStaggeredGridState] to be used by the grid.
 */
@Stable
class QuickNavStaggeredGridState(
    val gridState: LazyStaggeredGridState
) : QuickNavState {
    /**
     * Whether the "Scroll to Start" button should be displayed.
     */
    override val showScrollToStart by derivedStateOf {
        gridState.firstVisibleItemIndex > 0
    }

    /**
     * Whether the "Scroll to End" button should be displayed.
     */
    override val showScrollToEnd by derivedStateOf {
        val layoutInfo = gridState.layoutInfo
        val totalItems = layoutInfo.totalItemsCount
        if (totalItems == 0) return@derivedStateOf false
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        lastVisibleItem < totalItems - 1
    }

    /** Alias for [showScrollToStart]. */
    override val showScrollToPrevious get() = showScrollToStart

    /** Alias for [showScrollToEnd]. */
    override val showScrollToNext get() = showScrollToEnd

    /**
     * The current scroll progress as a percentage from 0.0 to 1.0.
     */
    override val scrollProgress: Float by derivedStateOf {
        val layoutInfo = gridState.layoutInfo
        val totalItems = layoutInfo.totalItemsCount
        val visibleItems = layoutInfo.visibleItemsInfo.size

        if (totalItems <= visibleItems) {
            0f
        } else {
            gridState.firstVisibleItemIndex.toFloat() / (totalItems - visibleItems)
        }
    }

    /** Scrolls smoothly to the first item in the grid. */
    override fun animateScrollToStart(scope: CoroutineScope) = scope.launch {
        gridState.animateScrollToItem(0)
    }

    /** Scrolls smoothly to the last item in the grid. */
    override fun animateScrollToEnd(scope: CoroutineScope) = scope.launch {
        val lastItem = gridState.layoutInfo.totalItemsCount - 1
        if (lastItem >= 0) gridState.animateScrollToItem(lastItem)
    }

    /** Scrolls smoothly back by approximately one visible viewport. */
    override fun animateScrollToPreviousPage(scope: CoroutineScope) = scope.launch {
        val visibleItemsCount = gridState.layoutInfo.visibleItemsInfo.size
        val targetIndex = (gridState.firstVisibleItemIndex - visibleItemsCount)
            .coerceAtLeast(0)
        gridState.animateScrollToItem(targetIndex)
    }

    /** Scrolls smoothly forward by approximately one visible viewport. */
    override fun animateScrollToNextPage(scope: CoroutineScope) = scope.launch {
        val visibleItemsCount = gridState.layoutInfo.visibleItemsInfo.size
        val maximumIndex = gridState.layoutInfo.totalItemsCount - 1
        val targetIndex = (gridState.firstVisibleItemIndex + visibleItemsCount)
            .coerceAtMost(maximumIndex)
        if (targetIndex >= 0) { gridState.animateScrollToItem(targetIndex) }
    }
}

/**
 * Creates and remembers a [QuickNavStaggeredGridState].
 */
@Composable
fun rememberQuickNavStaggeredGridState(
    gridState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
): QuickNavStaggeredGridState {
    return remember(gridState) {
        QuickNavStaggeredGridState(gridState)
    }
}
