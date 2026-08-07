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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.arml.composecollections.collections.layout.grid.CollectionGrid
import br.com.arml.composecollections.collections.layout.grid.CollectionStaggeredGrid
import br.com.arml.composecollections.collections.layout.list.CollectionList
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listState_scrollProgress_shouldCalculateCorrectly() {
        val state = LazyListState()
        val collectionState = CollectionListState(state)

        composeTestRule.setContent {
            CollectionList(
                listState = state,
                collectionState = collectionState
            ) {
                items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
            }
        }

        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 0f) }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(50) } }
        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle { assert(collectionState.scrollProgress > 0.4f && collectionState.scrollProgress < 0.6f) }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(100) } }
        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 1f) }
    }

    @Test
    fun gridState_scrollProgress_shouldCalculateCorrectly() {
        val state = LazyGridState()
        val collectionState = CollectionGridState(state)

        composeTestRule.setContent {
            CollectionGrid(
                cells = GridCells.Fixed(2),
                gridState = state,
                collectionState = collectionState
            ) {
                items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
            }
        }

        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 0f) }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(99) } }
        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 1f) }
    }

    @Test
    fun staggeredGridState_scrollProgress_shouldCalculateCorrectly() {
        val state = LazyStaggeredGridState()
        val collectionState = CollectionStaggeredGridState(state)

        composeTestRule.setContent {
            CollectionStaggeredGrid(
                cells = StaggeredGridCells.Fixed(2),
                gridState = state,
                collectionState = collectionState
            ) {
                items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
            }
        }

        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 0f) }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(99) } }
        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 1f) }
    }
}
