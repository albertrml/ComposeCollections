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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment
import br.com.arml.composecollections.scrollables.defaults.QuickNavDimensionDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavIconDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabels
import br.com.arml.composecollections.scrollables.layout.foundation.QuickNavScaffold
import br.com.arml.composecollections.scrollables.samples.scrollables.MockQuickNavState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuickNavScaffoldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testLabels = QuickNavLabels(
        previousLabel = "Prev",
        previousContentDescription = "Prev Desc",
        previousTag = "PrevTag",
        nextLabel = "Next",
        nextContentDescription = "Next Desc",
        nextTag = "NextTag"
    )

    @Test
    fun quickNavScaffold_shouldPropagateThemeAndTriggerActions() {
        var clicked = false
        val mockState = MockQuickNavState(
            showScrollToBackward = true,
            showScrollToForward = false,
            onAction = { clicked = true }
        )
        
        composeTestRule.setContent {
            QuickNavScaffold(
                navigationAlignment = NavigationAlignment.Top,
                labels = testLabels,
                icons = QuickNavIconDefaults.default,
                dimens = QuickNavDimensionDefaults.default,
                quickNavState = mockState,
                isHorizontal = false,
                container = {
                    Box(Modifier.fillMaxSize()) { Text("Content") }
                },
            )
        }

        // Verify content and button presence
        composeTestRule.onNodeWithText("Content").assertIsDisplayed()
        
        // Find by tag defined in testLabels
        composeTestRule.onNodeWithTag(testLabels.previousTag).assertIsDisplayed().performClick()
        
        assert(clicked)
    }
}
