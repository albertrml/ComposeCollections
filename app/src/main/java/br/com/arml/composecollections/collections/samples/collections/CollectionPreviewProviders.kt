/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.samples.collections

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.arml.composecollections.collections.state.CollectionState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

internal class MockCollectionState(
    override val showScrollToBackward: Boolean = false,
    override val showScrollToForward: Boolean = false,
    override val scrollProgress: Float = 0f,
    val onAction: (() -> Unit)? = null
) : CollectionState {
    override fun animateScrollToBackward(scope: CoroutineScope): Job {
        onAction?.invoke()
        return CompletableDeferred(Unit)
    }
    override fun animateScrollToForward(scope: CoroutineScope): Job {
        onAction?.invoke()
        return CompletableDeferred(Unit)
    }
    override fun animateScrollToStart(scope: CoroutineScope): Job {
        onAction?.invoke()
        return CompletableDeferred(Unit)
    }
    override fun animateScrollToEnd(scope: CoroutineScope): Job {
        onAction?.invoke()
        return CompletableDeferred(Unit)
    }
}

internal class CollectionStateProvider : PreviewParameterProvider<CollectionState> {
    override val values = sequenceOf(
        MockCollectionState(showScrollToBackward = false, showScrollToForward = true, scrollProgress = 0f),
        MockCollectionState(showScrollToBackward = true, showScrollToForward = true, scrollProgress = 0.5f),
        MockCollectionState(showScrollToBackward = true, showScrollToForward = false, scrollProgress = 1f)
    )
}
