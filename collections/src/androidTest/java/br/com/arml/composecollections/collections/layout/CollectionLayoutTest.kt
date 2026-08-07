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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.arml.composecollections.collections.defaults.CollectionDefaults
import br.com.arml.composecollections.collections.layout.list.CollectionPagedList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionLayoutTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val componentTag = CollectionDefaults.ComponentTestTag

    @Test
    fun collectionPagedList_withExpandLayoutFalse_shouldWrapHeight() {
        composeTestRule.setContent {
            CollectionPagedList(
                modifier = Modifier.fillMaxWidth(),
                expandLayout = false // Tight layout
            ) {
                item { Text("Small Content", modifier = Modifier.height(100.dp)) }
            }
        }

        composeTestRule.onNodeWithTag(componentTag).assertHeightIsAtLeast(100.dp)
    }

    @Test
    fun collectionPagedList_withExpandLayoutTrue_shouldFillHeight() {
        composeTestRule.setContent {
            CollectionPagedList(
                modifier = Modifier.fillMaxSize(),
                expandLayout = true // Stretch layout
            ) {
                item { Text("Small Content", modifier = Modifier.height(100.dp)) }
            }
        }

        composeTestRule.onNodeWithTag(componentTag).assertHeightIsAtLeast(300.dp)
    }
}
