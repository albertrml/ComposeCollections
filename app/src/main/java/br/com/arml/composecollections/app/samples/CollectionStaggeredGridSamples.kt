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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionLayoutSpec
import br.com.arml.composecollections.collections.layout.grid.CollectionEdgedStaggeredGrid
import br.com.arml.composecollections.collections.layout.grid.CollectionPagedStaggeredGrid
import kotlin.random.Random

@Composable
fun StaggeredItem(index: Int, isVertical: Boolean) {
    val randomSize = remember(index) { Random.nextInt(100, 300).dp }
    Box(
        modifier = Modifier
            .then(if (isVertical) Modifier.height(randomSize).fillMaxWidth() else Modifier.width(randomSize).height(150.dp))
            .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.medium)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = index.toString(), style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true, name = "Vertical Staggered Pinterest")
@Composable
fun VerticalStaggeredPinterestSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            CollectionEdgedStaggeredGrid(
                cells = StaggeredGridCells.Fixed(2),
                navigationAlignment = CollectionAlignment.End
            ) {
                items(100) { StaggeredItem(it, isVertical = true) }
            }
        }
    }
}

@Preview(showBackground = true, name = "Horizontal Staggered Grid")
@Composable
fun HorizontalStaggeredGridSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxWidth()) {
            CollectionPagedStaggeredGrid(
                cells = StaggeredGridCells.Adaptive(150.dp),
                layoutSpec = CollectionLayoutSpec.Horizontal(),
                navigationAlignment = CollectionAlignment.Bottom
            ) {
                items(100) { StaggeredItem(it, isVertical = false) }
            }
        }
    }
}
