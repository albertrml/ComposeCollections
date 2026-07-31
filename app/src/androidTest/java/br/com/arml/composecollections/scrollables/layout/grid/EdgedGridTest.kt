/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.layout.grid

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arml.composecollections.R
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutSpec
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for the generic [EdgedGrid] component in both Vertical and Horizontal orientations.
 */
@RunWith(AndroidJUnit4::class)
class EdgedGridTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var upButtonTag: String
    private lateinit var downButtonTag: String

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            upButtonTag = getString(R.string.quickNavList_upButton_testTag)
            downButtonTag = getString(R.string.quickNavList_downButton_testTag)
        }
    }

    @Test
    fun edgedGrid_vertical_shouldNavigateToExtremes() {
        val state = LazyGridState()
        composeTestRule.setContent {
            EdgedGrid(
                cells = GridCells.Fixed(3),
                gridState = state,
                layoutSpec = QuickNavLayoutSpec.Vertical()
            ) {
                items(100) { Text("Item $it", modifier = Modifier.height(100.dp).fillMaxWidth()) }
            }
        }

        composeTestRule.onNodeWithTag(downButtonTag).performClick()
        composeTestRule.waitForIdle()
        assert(state.firstVisibleItemIndex > 70)

        composeTestRule.onNodeWithTag(upButtonTag).performClick()
        composeTestRule.waitForIdle()
        assert(state.firstVisibleItemIndex == 0)
    }

    @Test
    fun edgedGrid_horizontal_shouldNavigateToExtremes() {
        val state = LazyGridState()
        composeTestRule.setContent {
            EdgedGrid(
                cells = GridCells.Fixed(3),
                gridState = state,
                layoutSpec = QuickNavLayoutSpec.Horizontal()
            ) {
                items(100) { Text("Item $it", modifier = Modifier.width(100.dp)) }
            }
        }

        composeTestRule.onNodeWithTag(downButtonTag).performClick()
        composeTestRule.waitForIdle()
        assert(state.firstVisibleItemIndex > 70)

        composeTestRule.onNodeWithTag(upButtonTag).performClick()
        composeTestRule.waitForIdle()
        assert(state.firstVisibleItemIndex == 0)
    }
}
