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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.arml.composecollections.collections.defaults.CollectionDimensionDefaults
import br.com.arml.composecollections.collections.defaults.CollectionLayoutSpec
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.defaults.CollectionTheme
import br.com.arml.composecollections.collections.layout.grid.CollectionGrid
import br.com.arml.composecollections.collections.layout.grid.CollectionStaggeredGrid
import br.com.arml.composecollections.collections.layout.list.CollectionList
import kotlinx.coroutines.CoroutineScope
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
            CollectionTheme(
                dimensions = CollectionDimensionDefaults.default.copy(itemSpacing = 0.dp)
            ) {
                Box(Modifier.height(500.dp)) {
                    CollectionList(
                        state = collectionState,
                        layoutSpec = CollectionLayoutSpec.Vertical(arrangement = Arrangement.Top)
                    ) {
                        items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
                    }
                }
            }
        }

        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 0f) }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(50) } }
        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle { assert(collectionState.scrollProgress > 0.4f) }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(99) } }
        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle { assert(collectionState.scrollProgress >= 0.99f) }
    }

    @Test
    fun gridState_scrollProgress_shouldCalculateCorrectly() {
        val state = LazyGridState()
        val collectionState = CollectionGridState(state)

        composeTestRule.setContent {
            CollectionTheme(
                dimensions = CollectionDimensionDefaults.default.copy(itemSpacing = 0.dp)
            ) {
                Box(Modifier.height(500.dp)) {
                    CollectionGrid(
                        cells = GridCells.Fixed(2),
                        state = collectionState,
                        layoutSpec = CollectionLayoutSpec.Vertical(arrangement = Arrangement.Top)
                    ) {
                        items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
                    }
                }
            }
        }

        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 0f) }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(99) } }
        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle { assert(collectionState.scrollProgress >= 0.99f) }
    }

    @Test
    fun staggeredGridState_scrollProgress_shouldCalculateCorrectly() {
        val state = LazyStaggeredGridState()
        val collectionState = CollectionStaggeredGridState(state)

        composeTestRule.setContent {
            CollectionTheme(
                dimensions = CollectionDimensionDefaults.default.copy(itemSpacing = 0.dp)
            ) {
                Box(Modifier.height(500.dp)) {
                    CollectionStaggeredGrid(
                        cells = StaggeredGridCells.Fixed(2),
                        state = collectionState,
                        layoutSpec = CollectionLayoutSpec.Vertical(arrangement = Arrangement.Top)
                    ) {
                        items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
                    }
                }
            }
        }

        composeTestRule.runOnIdle { assert(collectionState.scrollProgress == 0f) }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(99) } }
        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle { assert(collectionState.scrollProgress >= 0.99f) }
    }

    @Test
    fun listState_pageCalculation_shouldBeAccurate() {
        val state = LazyListState()
        val collectionState = CollectionListState(state)

        composeTestRule.setContent {
            CollectionTheme(
                dimensions = CollectionDimensionDefaults.default.copy(itemSpacing = 0.dp)
            ) {
                Box(Modifier.height(500.dp)) {
                    CollectionList(
                        state = collectionState,
                        layoutSpec = CollectionLayoutSpec.Vertical(arrangement = Arrangement.Top)
                    ) {
                        items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
                    }
                }
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle {
            assert(collectionState.totalPages in 19..21)
            assert(collectionState.currentPage == 1)
        }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(99) } }
        composeTestRule.waitForIdle()

        composeTestRule.runOnIdle {
            assert(collectionState.currentPage == collectionState.totalPages)
        }
    }

    @Test
    fun gridState_pageCalculation_shouldBeAccurate() {
        val state = LazyGridState()
        val collectionState = CollectionGridState(state)

        composeTestRule.setContent {
            CollectionTheme(
                dimensions = CollectionDimensionDefaults.default.copy(itemSpacing = 0.dp)
            ) {
                Box(Modifier.height(500.dp)) {
                    CollectionGrid(
                        cells = GridCells.Fixed(2),
                        state = collectionState,
                        layoutSpec = CollectionLayoutSpec.Vertical(arrangement = Arrangement.Top)
                    ) {
                        items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
                    }
                }
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle {
            assert(collectionState.totalPages in 9..11)
            assert(collectionState.currentPage == 1)
        }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(99) } }
        composeTestRule.waitForIdle()

        composeTestRule.runOnIdle {
            assert(collectionState.currentPage == collectionState.totalPages)
        }
    }

    @Test
    fun staggeredGridState_pageCalculation_shouldBeAccurate() {
        val state = LazyStaggeredGridState()
        val collectionState = CollectionStaggeredGridState(state)

        composeTestRule.setContent {
            CollectionTheme(
                dimensions = CollectionDimensionDefaults.default.copy(itemSpacing = 0.dp)
            ) {
                Box(Modifier.height(500.dp)) {
                    CollectionStaggeredGrid(
                        cells = StaggeredGridCells.Fixed(2),
                        state = collectionState,
                        layoutSpec = CollectionLayoutSpec.Vertical(arrangement = Arrangement.Top)
                    ) {
                        items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
                    }
                }
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle {
            assert(collectionState.totalPages > 0)
            assert(collectionState.currentPage == 1)
        }

        composeTestRule.runOnIdle { runBlocking { state.scrollToItem(99) } }
        composeTestRule.waitForIdle()

        composeTestRule.runOnIdle {
            assert(collectionState.currentPage == collectionState.totalPages)
        }
    }

    @Test
    fun pagerState_mapping_shouldBeAccurate() {
        val pageCount = 10
        composeTestRule.setContent {
            val pagerState = rememberPagerState { pageCount }
            val collectionState = rememberCollectionPagerState(pagerState)
            
            assert(collectionState.totalPages == pageCount)
            assert(collectionState.currentPage == 1)
            assert(collectionState.scrollProgress == 0f)
        }
    }

    @Test
    fun stepped_navigation_logic_should_honor_step() {
        val state = LazyListState()
        val step = 2
        val collectionState = CollectionListState(state, mode = CollectionMode.Stepped, step = step)
        var scope: CoroutineScope? = null

        composeTestRule.setContent {
            scope = rememberCoroutineScope()
            CollectionTheme(
                dimensions = CollectionDimensionDefaults.default.copy(itemSpacing = 0.dp)
            ) {
                Box(Modifier.height(500.dp)) {
                    CollectionList(
                        state = collectionState,
                        layoutSpec = CollectionLayoutSpec.Vertical(arrangement = Arrangement.Top)
                    ) {
                        items(100) { Box(Modifier.height(100.dp).fillMaxWidth()) }
                    }
                }
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.runOnIdle {
            collectionState.animateScrollToForward(scope!!)
        }
        composeTestRule.waitForIdle()

        composeTestRule.runOnIdle {
            assert(state.firstVisibleItemIndex > 0)
        }
    }
}
