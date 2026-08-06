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

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * An interface defining the contract for navigation state within QuickNav components.
 *
 * Implementing this interface allows for custom navigation logic, such as different
 * visibility triggers or specialized scroll animations, while remaining compatible
 * with the library's UI components.
 */
@Stable
interface QuickNavState {
    /**
     * Whether the "Backward" (Up/Start/Previous) navigation button should be displayed.
     */
    val showScrollToBackward: Boolean

    /**
     * Whether the "Forward" (Down/End/Next) navigation button should be displayed.
     */
    val showScrollToForward: Boolean

    /**
     * The current scroll progress as a percentage from 0.0 to 1.0.
     */
    val scrollProgress: Float

    /**
     * Smoothly scrolls in the backward direction (e.g., towards the start or previous page).
     *
     * @param scope The [CoroutineScope] to launch the animation in.
     */
    fun animateScrollToBackward(scope: CoroutineScope): Job

    /**
     * Smoothly scrolls in the forward direction (e.g., towards the end or next page).
     *
     * @param scope The [CoroutineScope] to launch the animation in.
     */
    fun animateScrollToForward(scope: CoroutineScope): Job

    /**
     * Smoothly scrolls to the absolute start of the collection.
     *
     * @param scope The [CoroutineScope] to launch the animation in.
     */
    fun animateScrollToStart(scope: CoroutineScope): Job

    /**
     * Smoothly scrolls to the absolute end of the collection.
     *
     * @param scope The [CoroutineScope] to launch the animation in.
     */
    fun animateScrollToEnd(scope: CoroutineScope): Job
}
