/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.layout

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arml.composecollections.R
import br.com.arml.composecollections.scrollables.layout.list.PagedList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuickNavIndicatorTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var indicatorTag: String

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            indicatorTag = getString(R.string.quickNav_indicator_testTag)
        }
    }

    @Test
    fun pagedList_withIndicatorEnabled_shouldDisplayIndicator() {
        composeTestRule.setContent {
            PagedList(
                showIndicator = true
            ) {
                items(100) { Text("Item $it") }
            }
        }

        composeTestRule.onNodeWithTag(indicatorTag).assertIsDisplayed()
    }

    @Test
    fun pagedList_withIndicatorDisabled_shouldNotDisplayIndicator() {
        composeTestRule.setContent {
            PagedList(
                showIndicator = false
            ) {
                items(100) { Text("Item $it") }
            }
        }

        composeTestRule.onNodeWithTag(indicatorTag).assertDoesNotExist()
    }
}
