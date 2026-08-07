/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arml.composecollections.R
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionGridStickyTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var headerOverlayTag: String

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            headerOverlayTag = getString(R.string.quickNav_scaffold_header_overlay_testTag)
        }
    }

    @Test
    fun collectionPagedGrid_withStickyHeaders_shouldUpdateOverlayOnScroll() {
        val state = LazyGridState()
        
        composeTestRule.setContent {
            CollectionPagedGrid(
                cells = GridCells.Fixed(2),
                gridState = state
            ) {
                stickyHeader { Text("Header 1") }
                items(20) { Box(Modifier.height(100.dp).fillMaxWidth()) }
                stickyHeader { Text("Header 2") }
                items(20) { Box(Modifier.height(100.dp).fillMaxWidth()) }
            }
        }

        // Verify Header 1
        composeTestRule.onNodeWithTag(headerOverlayTag).assert(hasAnyChild(hasText("Header 1")))

        // Scroll to Header 2
        composeTestRule.runOnIdle {
            runBlocking {
                state.scrollToItem(21)
            }
        }
        composeTestRule.waitForIdle()

        // Verify Header 2
        composeTestRule.onNodeWithTag(headerOverlayTag).assert(hasAnyChild(hasText("Header 2")))
    }
}
