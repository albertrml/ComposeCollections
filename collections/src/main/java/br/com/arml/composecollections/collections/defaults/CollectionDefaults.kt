/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.defaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import br.com.arml.composecollections.collections.components.CollectionNavigationRouter
import br.com.arml.composecollections.collections.state.CollectionState

/**
 * Global default values and constants for the Collection library.
 */
@Immutable
object CollectionDefaults {
    /**
     * Default layout expansion policy.
     */
    const val ExpandLayout = false

    /**
     * Test tag for the main collection component.
     */
    const val ComponentTestTag = "CollectionComponent"

    /**
     * Internal factory to create the default navigation UI.
     * This is used by the Scaffold when no custom controls are provided.
     */
    @Composable
    internal fun DefaultNavigationControl(
        alignment: CollectionAlignment,
        target: CollectionAlignment,
        secondaryTarget: CollectionAlignment,
        state: CollectionState,
        isHorizontal: Boolean,
        isStart: Boolean
    ) {
        CollectionNavigationRouter(
            alignment = alignment,
            target = target,
            secondaryTarget = secondaryTarget,
            collectionState = state,
            isHorizontal = isHorizontal,
            isStart = isStart
        )
    }
}
