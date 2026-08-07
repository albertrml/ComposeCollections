/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.arml.composecollections.collections.defaults.CollectionDefaults
import br.com.arml.composecollections.collections.layout.grid.CollectionPagedGrid
import br.com.arml.composecollections.collections.layout.list.CollectionPagedList
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionKeyboardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val componentTag = CollectionDefaults.ComponentTestTag

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun list_pageDownKey_shouldNavigateForward() {
        val state = LazyListState()
        composeTestRule.setContent {
            CollectionPagedList(listState = state) {
                items(100) { Text("Item $it", modifier = Modifier.height(100.dp).fillMaxWidth()) }
            }
        }

        composeTestRule.onNodeWithTag(componentTag).requestFocus()
        composeTestRule.onNodeWithTag(componentTag).performKeyInput { pressKey(Key.PageDown) }
        
        composeTestRule.waitForIdle()
        assert(state.firstVisibleItemIndex > 0)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun grid_homeKey_shouldNavigateToStart() {
        val state = LazyGridState()
        composeTestRule.setContent {
            CollectionPagedGrid(cells = GridCells.Fixed(2), gridState = state) {
                items(100) { Text("Item $it", modifier = Modifier.height(100.dp)) }
            }
        }

        composeTestRule.runOnIdle {
            runBlocking { state.scrollToItem(50) }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(componentTag).requestFocus()
        composeTestRule.onNodeWithTag(componentTag).performKeyInput { pressKey(Key.MoveHome) }
        
        composeTestRule.waitForIdle()
        assert(state.firstVisibleItemIndex == 0)
    }
}
