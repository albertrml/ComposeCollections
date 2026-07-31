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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.arml.composecollections.scrollables.layout.foundation.QuickNavLayout
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests the [br.com.arml.composecollections.scrollables.layout.foundation.QuickNavLayout] engine, verifying both Stacked and Overlay positioning.
 */
@RunWith(AndroidJUnit4::class)
class QuickNavLayoutTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun quickNavLayout_stackedMode_shouldDisplayAllContents() {
        composeTestRule.setContent {
            QuickNavLayout(
                isOverlay = false,
                contentTop = { Text("Top") },
                contentBottom = { Text("Bottom") },
                contentLeft = { Text("Left") },
                contentRight = { Text("Right") }
            ) {
                Box(Modifier.size(100.dp)) { Text("Content") }
            }
        }

        composeTestRule.onNodeWithText("Top").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bottom").assertIsDisplayed()
        composeTestRule.onNodeWithText("Left").assertIsDisplayed()
        composeTestRule.onNodeWithText("Right").assertIsDisplayed()
        composeTestRule.onNodeWithText("Content").assertIsDisplayed()
    }

    @Test
    fun quickNavLayout_overlayMode_shouldDisplayAllContents() {
        composeTestRule.setContent {
            QuickNavLayout(
                isOverlay = true,
                contentTop = { Text("Top") },
                contentBottom = { Text("Bottom") },
                contentLeft = { Text("Left") },
                contentRight = { Text("Right") }
            ) {
                Box(Modifier.fillMaxSize()) { Text("Content") }
            }
        }

        composeTestRule.onNodeWithText("Top").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bottom").assertIsDisplayed()
        composeTestRule.onNodeWithText("Left").assertIsDisplayed()
        composeTestRule.onNodeWithText("Right").assertIsDisplayed()
        composeTestRule.onNodeWithText("Content").assertIsDisplayed()
    }
}
