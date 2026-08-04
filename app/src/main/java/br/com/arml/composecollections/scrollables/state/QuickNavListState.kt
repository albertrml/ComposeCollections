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

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Default implementation of [QuickNavState] for LazyLists.
 *
 * This class tracks the current scroll position and determines if navigation buttons
 * should be shown based on the visibility of the first and last items.
 *
 * @param listState The [LazyListState] to be used by the list.
 */
@Stable
class QuickNavListState(
    val listState: LazyListState
) : QuickNavState {
    /**
     * Whether the "Scroll to Start" button should be displayed.
     */
    override val showScrollToStart by derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }

    /**
     * Whether the "Scroll to End" button should be displayed.
     */
    override val showScrollToEnd by derivedStateOf {
        val layoutInfo = listState.layoutInfo
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
        val layoutInfo = listState.layoutInfo
        val totalItems = layoutInfo.totalItemsCount
        val visibleItems = layoutInfo.visibleItemsInfo.size
        
        if (totalItems <= visibleItems) {
            0f
        } else {
            listState.firstVisibleItemIndex.toFloat() / (totalItems - visibleItems)
        }
    }

    /** Scrolls smoothly to the first item in the list. */
    override fun animateScrollToStart(scope: CoroutineScope) = scope.launch {
        listState.animateScrollToItem(0)
    }

    /** Scrolls smoothly to the last item in the list. */
    override fun animateScrollToEnd(scope: CoroutineScope) = scope.launch {
        with(listState){
            val lastItem = layoutInfo.totalItemsCount - 1
            if (lastItem >= 0) animateScrollToItem(lastItem)
        }
    }

    /** Scrolls smoothly back by approximately one visible viewport. */
    override fun animateScrollToPreviousPage(scope: CoroutineScope) = scope.launch {
        with(listState){
            val visibleItemsCount = layoutInfo.visibleItemsInfo.size
            val targetIndex = (firstVisibleItemIndex - visibleItemsCount)
                .coerceAtLeast(0)
            animateScrollToItem(targetIndex)
        }
    }

    /** Scrolls smoothly forward by approximately one visible viewport. */
    override fun animateScrollToNextPage(scope: CoroutineScope) = scope.launch {
        with(listState){
            val visibleItemsCount = layoutInfo.visibleItemsInfo.size
            val maximumIndex = layoutInfo.totalItemsCount - 1
            val targetIndex = (firstVisibleItemIndex + visibleItemsCount)
                .coerceAtMost(maximumIndex)
            if (targetIndex >= 0) { animateScrollToItem(targetIndex) }
        }
    }
}

/**
 * Creates and remembers a [QuickNavListState].
 */
@Composable
fun rememberQuickNavListState(
    listState: LazyListState = rememberLazyListState()
): QuickNavListState {
    return remember(listState) {
        QuickNavListState(listState)
    }
}
