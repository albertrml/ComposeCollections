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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * An interface defining the contract for navigation state within QuickNav components.
 *
 * Implementing this interface allows for custom navigation logic, such as different
 * visibility triggers or specialized scroll animations, while remaining compatible
 * with the library's UI components.
 */
interface QuickNavState {
    /**
     * Whether the "Scroll to Start" button should be displayed.
     */
    val showScrollToStart: Boolean

    /**
     * Whether the "Scroll to End" button should be displayed.
     */
    val showScrollToEnd: Boolean

    /**
     * Whether the "Scroll to Previous" (Backward) button should be displayed.
     */
    val showScrollToPrevious: Boolean

    /**
     * Whether the "Scroll to Next" (Forward) button should be displayed.
     */
    val showScrollToNext: Boolean

    /**
     * Smoothly scrolls to the starting extreme of the collection.
     *
     * @param scope The [CoroutineScope] to launch the animation in.
     */
    fun animateScrollToStart(scope: CoroutineScope): Job

    /**
     * Smoothly scrolls to the ending extreme of the collection.
     *
     * @param scope The [CoroutineScope] to launch the animation in.
     */
    fun animateScrollToEnd(scope: CoroutineScope): Job

    /**
     * Smoothly scrolls back by approximately one visible viewport.
     *
     * @param scope The [CoroutineScope] to launch the animation in.
     */
    fun animateScrollToPreviousPage(scope: CoroutineScope): Job

    /**
     * Smoothly scrolls forward by approximately one visible viewport.
     *
     * @param scope The [CoroutineScope] to launch the animation in.
     */
    fun animateScrollToNextPage(scope: CoroutineScope): Job
}
