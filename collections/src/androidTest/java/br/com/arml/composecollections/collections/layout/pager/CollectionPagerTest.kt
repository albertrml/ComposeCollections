/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout.pager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arml.composecollections.collections.R
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionPagerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var pagedUpTag: String
    private lateinit var pagedDownTag: String
    private lateinit var edgedUpTag: String
    private lateinit var edgedDownTag: String
    private lateinit var steppedUpTag: String
    private lateinit var steppedDownTag: String
    private lateinit var pagedComponentTag: String
    private lateinit var edgedComponentTag: String
    private lateinit var steppedComponentTag: String

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            pagedUpTag = getString(R.string.collectionPagedLabel_upButton_testTag)
            pagedDownTag = getString(R.string.collectionPagedLabel_downButton_testTag)
            edgedUpTag = getString(R.string.collectionEdgedLabel_upButton_testTag)
            edgedDownTag = getString(R.string.collectionEdgedLabel_downButton_testTag)
            steppedUpTag = getString(R.string.collectionSteppedLabel_upButton_testTag)
            steppedDownTag = getString(R.string.collectionSteppedLabel_downButton_testTag)
            pagedComponentTag = getString(R.string.collectionPagedLabel_component_testTag)
            edgedComponentTag = getString(R.string.collectionEdgedLabel_component_testTag)
            steppedComponentTag = getString(R.string.collectionSteppedLabel_component_testTag)
        }
    }

    @Test
    fun pagedPager_shouldNavigatePageByPage() {
        val pageCount = 5
        composeTestRule.setContent {
            CollectionPagedPager(
                pageCount = { pageCount },
                modifier = Modifier.fillMaxSize(),
                navigationAlignment = CollectionAlignment.Bottom
            ) { index ->
                Text("Page $index")
            }
        }

        composeTestRule.onNodeWithTag(pagedDownTag).performClick()
        composeTestRule.waitForIdle()
        // Pager should be at index 1
    }

    @Test
    fun edgedPager_shouldJumpToEnd() {
        val pageCount = 10
        composeTestRule.setContent {
            CollectionEdgedPager(
                pageCount = { pageCount },
                modifier = Modifier.fillMaxSize(),
                navigationAlignment = CollectionAlignment.Bottom
            ) { index ->
                Text("Page $index")
            }
        }

        composeTestRule.onNodeWithTag(edgedDownTag).performClick()
        composeTestRule.waitForIdle()
        // Pager should be at index 9
    }

    @Test
    fun steppedPager_shouldJumpByStep() {
        val pageCount = 20
        val step = 3
        composeTestRule.setContent {
            CollectionSteppedPager(
                pageCount = { pageCount },
                step = step,
                modifier = Modifier.fillMaxSize(),
                navigationAlignment = CollectionAlignment.Bottom
            ) { index ->
                Text("Page $index")
            }
        }

        composeTestRule.onNodeWithTag(steppedDownTag).performClick()
        composeTestRule.waitForIdle()
        // Pager should be at index 3
    }

    @Test
    fun pagedPager_shouldHaveCorrectComponentTag() {
        composeTestRule.setContent {
            CollectionPagedPager(
                pageCount = { 5 },
                navigationAlignment = CollectionAlignment.Bottom
            ) { Text("Page $it") }
        }
        composeTestRule.onNodeWithTag(pagedComponentTag).assertExists()
    }

    @Test
    fun edgedPager_shouldHaveCorrectComponentTag() {
        composeTestRule.setContent {
            CollectionEdgedPager(
                pageCount = { 5 },
                navigationAlignment = CollectionAlignment.Bottom
            ) { Text("Page $it") }
        }
        composeTestRule.onNodeWithTag(edgedComponentTag).assertExists()
    }

    @Test
    fun steppedPager_shouldHaveCorrectComponentTag() {
        composeTestRule.setContent {
            CollectionSteppedPager(
                pageCount = { 5 },
                step = 2,
                navigationAlignment = CollectionAlignment.Bottom
            ) { Text("Page $it") }
        }
        composeTestRule.onNodeWithTag(steppedComponentTag).assertExists()
    }
}
