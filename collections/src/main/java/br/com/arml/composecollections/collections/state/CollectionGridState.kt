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
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import br.com.arml.composecollections.collections.defaults.CollectionAnimationMode
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.defaults.getCollectionAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * State object for Collection Grid components.
 */
@Stable
open class CollectionGridState(
    val gridState: LazyGridState,
    override val mode: CollectionMode = CollectionMode.Edged,
    override val animationSpec: AnimationSpec<Float>? = null,
    step: Int = 2
) : CollectionState {

    val step = step.coerceAtLeast(2)

    override val isScrolling by derivedStateOf { gridState.isScrollInProgress }

    override val showScrollToBackward by derivedStateOf {
        gridState.firstVisibleItemIndex > 0 || gridState.firstVisibleItemScrollOffset > 0
    }

    override val showScrollToForward by derivedStateOf {
        val layoutInfo = gridState.layoutInfo
        val totalItems = layoutInfo.totalItemsCount
        if (totalItems == 0) return@derivedStateOf false
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        lastVisibleItem < totalItems - 1
    }

    override val scrollProgress: Float by derivedStateOf {
        val layoutInfo = gridState.layoutInfo
        val totalItems = layoutInfo.totalItemsCount
        if (totalItems == 0) return@derivedStateOf 0f

        val visibleItems = layoutInfo.visibleItemsInfo
        if (visibleItems.isEmpty()) return@derivedStateOf 0f

        val firstVisible = visibleItems.first()
        val lastVisible = visibleItems.last()
        val isVertical = layoutInfo.orientation == Orientation.Vertical
        
        val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
        val totalMeasuredSize = lastVisible.offset.mainAxis(isVertical) + lastVisible.size.mainAxis(isVertical) - firstVisible.offset.mainAxis(isVertical)
        val estimatedTotalSize = (totalMeasuredSize.toFloat() / visibleItems.size) * totalItems
        val scrolledPixels = (firstVisible.index * (totalMeasuredSize / visibleItems.size)) + gridState.firstVisibleItemScrollOffset
        
        val progress = scrolledPixels.toFloat() / (estimatedTotalSize - viewportSize)
        progress.coerceIn(0f, 1f)
    }

    override val currentPage: Int by derivedStateOf {
        val total = totalPages
        if (total <= 1) return@derivedStateOf 1

        // Use scroll progress to interpolate for a smooth and accurate page count.
        // We apply a small threshold to ensure it reaches the last page at the physical end.
        val progress = if (scrollProgress > 0.99f) 1f else scrollProgress
        (progress * (total - 1)).roundToInt() + 1
    }

    override val totalPages: Int by derivedStateOf {
        val layoutInfo = gridState.layoutInfo
        val totalItems = layoutInfo.totalItemsCount
        if (totalItems == 0) return@derivedStateOf 1
        val visibleItems = layoutInfo.visibleItemsInfo
        if (visibleItems.isEmpty()) return@derivedStateOf 1

        val isVertical = layoutInfo.orientation == Orientation.Vertical
        val firstVisible = visibleItems.first()
        val lastVisible = visibleItems.last()
        val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset

        // Distance from start of first visible to end of last visible along main axis
        val totalMeasuredSize = lastVisible.offset.mainAxis(isVertical) + lastVisible.size.mainAxis(isVertical) - firstVisible.offset.mainAxis(isVertical)
        if (totalMeasuredSize <= 0) return@derivedStateOf 1

        // Use index span to accurately reflect item density (accounts for columns/rows correctly)
        val indexSpan = (lastVisible.index - firstVisible.index + 1).toDouble()
        val itemsPerPage = (indexSpan / totalMeasuredSize) * viewportSize

        ceil((totalItems.toDouble() / itemsPerPage) - 0.001).toInt().coerceAtLeast(1)
    }

    override fun animateScrollToBackward(scope: CoroutineScope) = scope.launch {
        when (mode) {
            CollectionMode.Edged -> animateScrollToStart(scope)
            CollectionMode.Paged, CollectionMode.Stepped -> animateScrollToPreviousPage(scope)
        }
    }

    override fun animateScrollToForward(scope: CoroutineScope) = scope.launch {
        when (mode) {
            CollectionMode.Edged -> animateScrollToEnd(scope)
            CollectionMode.Paged, CollectionMode.Stepped -> animateScrollToNextPage(scope)
        }
    }

    override fun animateScrollToStart(scope: CoroutineScope) = scope.launch {
        gridState.animateScrollToItem(0)
    }

    override fun animateScrollToEnd(scope: CoroutineScope) = scope.launch {
        val lastItem = gridState.layoutInfo.totalItemsCount - 1
        if (lastItem >= 0) gridState.animateScrollToItem(lastItem)
    }

    private fun animateScrollToPreviousPage(scope: CoroutineScope) = scope.launch {
        val visibleItemsCount = gridState.layoutInfo.visibleItemsInfo.size
        val targetIndex = (gridState.firstVisibleItemIndex - (visibleItemsCount * step)).coerceAtLeast(0)
        gridState.animateScrollToItem(targetIndex)
    }

    private fun animateScrollToNextPage(scope: CoroutineScope) = scope.launch {
        val visibleItemsCount = gridState.layoutInfo.visibleItemsInfo.size
        val maximumIndex = gridState.layoutInfo.totalItemsCount - 1
        val targetIndex = (gridState.firstVisibleItemIndex + (visibleItemsCount * step)).coerceAtMost(maximumIndex)
        if (targetIndex >= 0) { gridState.animateScrollToItem(targetIndex) }
    }
    
    private fun IntOffset.mainAxis(isVertical: Boolean) = if (isVertical) y else x
    private fun IntSize.mainAxis(isVertical: Boolean) = if (isVertical) height else width
}

/**
 * Creates and remembers a [CollectionGridState].
 */
@Composable
fun rememberCollectionGridState(
    gridState: LazyGridState = rememberLazyGridState(),
    mode: CollectionMode = CollectionMode.Paged,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    step: Int = 1
): CollectionGridState {
    val spec = getCollectionAnimation(animationMode)
    return remember(gridState, mode, spec, step) {
        CollectionGridState(gridState, mode, spec, step)
    }
}
