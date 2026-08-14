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
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arml.composecollections.collections.R
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.layout.list.CollectionPagedList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionSlotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var defaultButtonTag: String

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            defaultButtonTag = getString(R.string.pagedQuickNavList_downButton_testTag)
        }
    }

    @Test
    fun collectionPagedList_withCustomSlot_shouldRenderCustomControl_evenIfAlignmentIsNone() {
        composeTestRule.setContent {
            CollectionPagedList(
                navigationAlignment = CollectionAlignment.None,
                forwardControl = { _ ->
                    Text("Custom Forward Control")
                }
            ) {
                items(100) { Text("Item $it") }
            }
        }

        composeTestRule.onNodeWithText("Custom Forward Control").assertIsDisplayed()
        // Default button should NOT exist
        composeTestRule.onNodeWithTag(defaultButtonTag).assertDoesNotExist()
    }

    @Test
    fun collectionPagedList_withCustomSlot_shouldOverrideDefaultButton() {
        composeTestRule.setContent {
            CollectionPagedList(
                navigationAlignment = CollectionAlignment.Bottom, // Would normally show default buttons
                forwardControl = { _ ->
                    Text("I Win")
                }
            ) {
                items(100) { Text("Item $it") }
            }
        }

        composeTestRule.onNodeWithText("I Win").assertIsDisplayed()
        // The default "Next" button should be hidden by the custom slot
        composeTestRule.onNodeWithTag(defaultButtonTag).assertDoesNotExist()
    }
}
