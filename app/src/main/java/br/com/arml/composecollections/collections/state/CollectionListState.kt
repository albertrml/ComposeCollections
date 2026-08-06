/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.state

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.arml.composecollections.collections.defaults.CollectionAnimationMode
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.defaults.getCollectionAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * State object for Collection List components.
 */
@Stable
open class CollectionListState(
    val listState: LazyListState,
    val mode: CollectionMode = CollectionMode.Edged,
    val animationSpec: AnimationSpec<Float>? = null
) : CollectionState {

    override val showScrollToBackward by derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }

    override val showScrollToForward by derivedStateOf {
        val layoutInfo = listState.layoutInfo
        val totalItems = layoutInfo.totalItemsCount
        if (totalItems == 0) return@derivedStateOf false
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        lastVisibleItem < totalItems - 1
    }

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

    override fun animateScrollToBackward(scope: CoroutineScope) = scope.launch {
        when (mode) {
            CollectionMode.Edged -> animateScrollToStart(scope)
            CollectionMode.Paged -> animateScrollToPreviousPage(scope)
        }
    }

    override fun animateScrollToForward(scope: CoroutineScope) = scope.launch {
        when (mode) {
            CollectionMode.Edged -> animateScrollToEnd(scope)
            CollectionMode.Paged -> animateScrollToNextPage(scope)
        }
    }

    override fun animateScrollToStart(scope: CoroutineScope) = scope.launch {
        listState.animateScrollToItem(0)
    }

    override fun animateScrollToEnd(scope: CoroutineScope) = scope.launch {
        val lastItem = listState.layoutInfo.totalItemsCount - 1
        if (lastItem >= 0) listState.animateScrollToItem(lastItem)
    }

    private fun animateScrollToPreviousPage(scope: CoroutineScope) = scope.launch {
        val visibleItemsCount = listState.layoutInfo.visibleItemsInfo.size
        val targetIndex = (listState.firstVisibleItemIndex - visibleItemsCount).coerceAtLeast(0)
        listState.animateScrollToItem(targetIndex)
    }

    private fun animateScrollToNextPage(scope: CoroutineScope) = scope.launch {
        val visibleItemsCount = listState.layoutInfo.visibleItemsInfo.size
        val maximumIndex = listState.layoutInfo.totalItemsCount - 1
        val targetIndex = (listState.firstVisibleItemIndex + visibleItemsCount).coerceAtMost(maximumIndex)
        if (targetIndex >= 0) { listState.animateScrollToItem(targetIndex) }
    }
}

/**
 * Creates and remembers a [CollectionListState].
 */
@Composable
fun rememberCollectionListState(
    listState: LazyListState = rememberLazyListState(),
    mode: CollectionMode = CollectionMode.Edged,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default
): CollectionListState {
    val spec = getCollectionAnimation(animationMode)
    return remember(listState, mode, spec) {
        CollectionListState(listState, mode, spec)
    }
}
