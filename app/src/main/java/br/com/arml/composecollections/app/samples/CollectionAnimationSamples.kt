/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.app.samples

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionAnimationMode
import br.com.arml.composecollections.collections.layout.list.CollectionPagedList

@Preview(showBackground = true, name = "Elastic Scroll Sample")
@Composable
fun ElasticScrollSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            CollectionPagedList(
                animationMode = CollectionAnimationMode.Elastic,
                navigationAlignment = CollectionAlignment.Bottom,
                showIndicator = true
            ) {
                items(100) { ListItem(it) }
            }
        }
    }
}

@Preview(showBackground = true, name = "Snap Scroll Sample")
@Composable
fun SnapScrollSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            CollectionPagedList(
                animationMode = CollectionAnimationMode.Snap,
                navigationAlignment = CollectionAlignment.Bottom,
                showIndicator = true
            ) {
                items(100) { ListItem(it) }
            }
        }
    }
}
