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

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.arml.composecollections.scrollables.defaults.QuickNavAnimationMode
import br.com.arml.composecollections.scrollables.defaults.QuickNavMode
import br.com.arml.composecollections.scrollables.defaults.getQuickNavAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * State object for QuickNav StaggeredGrid components.
 *
 * @param gridState The [LazyStaggeredGridState] to be used by the grid.
 * @param mode The navigation mode (Edged or Paged).
 * @param animationSpec The [AnimationSpec] to be used for scroll animations.
 */
@Stable
open class QuickNavStaggeredGridState(
    val gridState: LazyStaggeredGridState,
    val mode: QuickNavMode = QuickNavMode.Edged,
    val animationSpec: AnimationSpec<Float>? = null
) : QuickNavState {

    override val showScrollToBackward by derivedStateOf {
        gridState.firstVisibleItemIndex > 0
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
        val visibleItems = layoutInfo.visibleItemsInfo.size

        if (totalItems <= visibleItems) {
            0f
        } else {
            gridState.firstVisibleItemIndex.toFloat() / (totalItems - visibleItems)
        }
    }

    override fun animateScrollToBackward(scope: CoroutineScope) = scope.launch {
        when (mode) {
            QuickNavMode.Edged -> gridState.animateScrollToItem(0)
            QuickNavMode.Paged -> {
                val visibleItemsCount = gridState.layoutInfo.visibleItemsInfo.size
                val targetIndex = (gridState.firstVisibleItemIndex - visibleItemsCount).coerceAtLeast(0)
                gridState.animateScrollToItem(targetIndex)
            }
        }
    }

    override fun animateScrollToForward(scope: CoroutineScope) = scope.launch {
        when (mode) {
            QuickNavMode.Edged -> {
                val lastItem = gridState.layoutInfo.totalItemsCount - 1
                if (lastItem >= 0) gridState.animateScrollToItem(lastItem)
            }
            QuickNavMode.Paged -> {
                val visibleItemsCount = gridState.layoutInfo.visibleItemsInfo.size
                val maximumIndex = gridState.layoutInfo.totalItemsCount - 1
                val targetIndex = (gridState.firstVisibleItemIndex + visibleItemsCount).coerceAtMost(maximumIndex)
                if (targetIndex >= 0) { gridState.animateScrollToItem(targetIndex) }
            }
        }
    }

    // Deprecated bridge methods
    @Deprecated("Use animateScrollToBackward", ReplaceWith("animateScrollToBackward(scope)"))
    fun animateScrollToStart(scope: CoroutineScope) = animateScrollToBackward(scope)
    @Deprecated("Use animateScrollToForward", ReplaceWith("animateScrollToForward(scope)"))
    fun animateScrollToEnd(scope: CoroutineScope) = animateScrollToForward(scope)
    @Deprecated("Use animateScrollToBackward", ReplaceWith("animateScrollToBackward(scope)"))
    fun animateScrollToPreviousPage(scope: CoroutineScope) = animateScrollToBackward(scope)
    @Deprecated("Use animateScrollToForward", ReplaceWith("animateScrollToForward(scope)"))
    fun animateScrollToNextPage(scope: CoroutineScope) = animateScrollToForward(scope)
}

/**
 * Creates and remembers a [QuickNavStaggeredGridState] with the specified mode and animation mode.
 */
@Composable
fun rememberQuickNavStaggeredGridState(
    gridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    mode: QuickNavMode = QuickNavMode.Edged,
    animationMode: QuickNavAnimationMode = QuickNavAnimationMode.Default
): QuickNavStaggeredGridState {
    val spec = getQuickNavAnimation(animationMode)
    return remember(gridState, mode, spec) {
        QuickNavStaggeredGridState(gridState, mode, spec)
    }
}
