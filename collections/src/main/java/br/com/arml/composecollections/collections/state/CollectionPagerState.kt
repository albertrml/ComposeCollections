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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
 * State object for Collection Pager components.
 */
@Stable
open class CollectionPagerState(
    val pagerState: PagerState,
    override val mode: CollectionMode = CollectionMode.Paged,
    override val animationSpec: AnimationSpec<Float>? = null,
    step: Int = 2
) : CollectionState {

    val step = step.coerceAtLeast(2)

    override val isScrolling by derivedStateOf { pagerState.isScrollInProgress }

    override val showScrollToBackward by derivedStateOf {
        pagerState.currentPage > 0
    }

    override val showScrollToForward by derivedStateOf {
        pagerState.currentPage < pagerState.pageCount - 1
    }

    override val scrollProgress: Float by derivedStateOf {
        val total = pagerState.pageCount
        if (total <= 1) return@derivedStateOf 0f
        
        val progress = (pagerState.currentPage + pagerState.currentPageOffsetFraction) / (total - 1)
        progress.coerceIn(0f, 1f)
    }

    override val currentPage: Int by derivedStateOf {
        pagerState.currentPage + 1
    }

    override val totalPages: Int by derivedStateOf {
        pagerState.pageCount
    }

    override fun animateScrollToBackward(scope: CoroutineScope) = scope.launch {
        val targetPage = if (mode == CollectionMode.Paged || mode == CollectionMode.Stepped) {
            (pagerState.currentPage - step).coerceAtLeast(0)
        } else {
            0
        }
        pagerState.animateScrollToPage(targetPage)
    }

    override fun animateScrollToForward(scope: CoroutineScope) = scope.launch {
        val targetPage = if (mode == CollectionMode.Paged || mode == CollectionMode.Stepped) {
            (pagerState.currentPage + step).coerceAtMost(pagerState.pageCount - 1)
        } else {
            (pagerState.pageCount - 1).coerceAtLeast(0)
        }
        pagerState.animateScrollToPage(targetPage)
    }

    override fun animateScrollToStart(scope: CoroutineScope) = scope.launch {
        pagerState.animateScrollToPage(0)
    }

    override fun animateScrollToEnd(scope: CoroutineScope) = scope.launch {
        if (pagerState.pageCount > 0) {
            pagerState.animateScrollToPage(pagerState.pageCount - 1)
        }
    }
}

/**
 * Creates and remembers a [CollectionPagerState].
 */
@Composable
fun rememberCollectionPagerState(
    pagerState: PagerState,
    mode: CollectionMode = CollectionMode.Paged,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    step: Int = 1
): CollectionPagerState {
    val spec = getCollectionAnimation(animationMode)
    return remember(pagerState, mode, spec, step) {
        CollectionPagerState(pagerState, mode, spec, step)
    }
}

/**
 * Creates and remembers a [CollectionPagerState] with internal [PagerState].
 */
@Composable
fun rememberCollectionPagerState(
    pageCount: () -> Int,
    mode: CollectionMode = CollectionMode.Paged,
    animationMode: CollectionAnimationMode = CollectionAnimationMode.Default,
    step: Int = 1
): CollectionPagerState {
    val pagerState = rememberPagerState(pageCount = pageCount)
    return rememberCollectionPagerState(pagerState, mode, animationMode, step)
}
