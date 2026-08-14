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
import androidx.compose.runtime.Stable
import br.com.arml.composecollections.collections.defaults.CollectionMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * Interface defining the logical state and telemetry of a collection container.
 */
@Stable
interface CollectionState {
    /**
     * The active navigation mode ([CollectionMode.Paged] or [CollectionMode.Edged]).
     */
    val mode: CollectionMode

    /**
     * Custom animation for programmatic scrolls.
     */
    val animationSpec: AnimationSpec<Float>?

    /**
     * Whether the collection is currently being scrolled.
     */
    val isScrolling: Boolean

    /**
     * Whether to show the button to scroll to a backward position.
     */
    val showScrollToBackward: Boolean

    /**
     * Whether to show the button to scroll to a forward position.
     */
    val showScrollToForward: Boolean

    /**
     * High-precision scroll progress from 0.0 (start) to 1.0 (end).
     */
    val scrollProgress: Float

    /**
     * The current page index (1-based).
     */
    val currentPage: Int

    /**
     * Estimated total number of pages based on viewport size.
     */
    val totalPages: Int

    /**
     * Navigates backward based on [mode].
     */
    fun animateScrollToBackward(scope: CoroutineScope): Job

    /**
     * Navigates forward based on [mode].
     */
    fun animateScrollToForward(scope: CoroutineScope): Job

    /**
     * Directly navigates to the absolute start of the collection.
     */
    fun animateScrollToStart(scope: CoroutineScope): Job

    /**
     * Directly navigates to the absolute end of the collection.
     */
    fun animateScrollToEnd(scope: CoroutineScope): Job
}
