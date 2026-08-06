/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.samples.scrollables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.scrollables.defaults.QuickNavDimensionDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavIconDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabelDefaults
import br.com.arml.composecollections.scrollables.internal.QuickNavLinearIndicator
import br.com.arml.composecollections.scrollables.layout.foundation.QuickNavScaffold
import br.com.arml.composecollections.scrollables.state.QuickNavState

/**
 * A sample that demonstrates how to use [QuickNavStateProvider] to visualize
 * different UI states (Start, Middle, End) in the Android Studio Preview.
 */
@Preview(showBackground = true, name = "QuickNav State Showcase")
@Composable
fun QuickNavStateShowcasePreview(
    @PreviewParameter(QuickNavStateProvider::class) state: QuickNavState
) {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            QuickNavScaffold(
                quickNavState = state,
                labels = QuickNavLabelDefaults.pagedLabels(),
                icons = QuickNavIconDefaults.default,
                dimens = QuickNavDimensionDefaults.default,
                isHorizontal = false,
                indicator = {
                    QuickNavLinearIndicator(
                        progress = state.scrollProgress,
                        isHorizontal = false
                    )
                }
            ) { modifier ->
                Box(
                    modifier = modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Scroll Progress: ${(state.scrollProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                }
            }
        }
    }
}
