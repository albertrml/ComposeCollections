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

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arml.composecollections.R
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.layout.list.CollectionList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionGeneralistTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var pagedDownTag: String
    private lateinit var edgedDownTag: String

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            pagedDownTag = getString(R.string.pagedQuickNavList_downButton_testTag)
            edgedDownTag = getString(R.string.quickNavList_downButton_testTag)
        }
    }

    @Test
    fun collectionList_withPagedMode_shouldShowPagedButtons() {
        composeTestRule.setContent {
            CollectionList(
                mode = CollectionMode.Paged,
                navigationAlignment = CollectionAlignment.Bottom
            ) {
                items(100) { Text("Item $it") }
            }
        }

        composeTestRule.onNodeWithTag(pagedDownTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(edgedDownTag).assertDoesNotExist()
    }

    @Test
    fun collectionList_withEdgedMode_shouldShowEdgedButtons() {
        composeTestRule.setContent {
            CollectionList(
                mode = CollectionMode.Edged,
                navigationAlignment = CollectionAlignment.Bottom
            ) {
                items(100) { Text("Item $it") }
            }
        }

        composeTestRule.onNodeWithTag(edgedDownTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(pagedDownTag).assertDoesNotExist()
    }

    @Test
    fun collectionList_withNoneAlignment_shouldShowNoButtons() {
        composeTestRule.setContent {
            CollectionList(
                navigationAlignment = CollectionAlignment.None
            ) {
                items(100) { Text("Item $it") }
            }
        }

        composeTestRule.onNodeWithTag(pagedDownTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(edgedDownTag).assertDoesNotExist()
    }
}
