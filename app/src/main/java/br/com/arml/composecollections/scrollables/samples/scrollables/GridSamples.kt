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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutSpec
import br.com.arml.composecollections.scrollables.layout.grid.EdgedGrid
import br.com.arml.composecollections.scrollables.layout.grid.PagedGrid

@Composable
private fun GridItem(index: Int) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.medium)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = index.toString(), style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true, name = "Vertical Edged Grid")
@Composable
fun VerticalEdgedGridSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            EdgedGrid(
                cells = GridCells.Fixed(3),
                layoutSpec = QuickNavLayoutSpec.Vertical()
            ) {
                items(100) { GridItem(it) }
            }
        }
    }
}

@Preview(showBackground = true, name = "Vertical Paged Grid")
@Composable
fun VerticalPagedGridSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            PagedGrid(
                cells = GridCells.Adaptive(100.dp),
                layoutSpec = QuickNavLayoutSpec.Vertical()
            ) {
                items(100) { GridItem(it) }
            }
        }
    }
}

@Preview(showBackground = true, name = "Horizontal Edged Grid")
@Composable
fun HorizontalEdgedGridSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            EdgedGrid(
                cells = GridCells.Fixed(4),
                layoutSpec = QuickNavLayoutSpec.Horizontal()
            ) {
                items(100) { GridItem(it) }
            }
        }
    }
}

@Preview(showBackground = true, name = "Horizontal Paged Grid")
@Composable
fun HorizontalPagedGridSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            PagedGrid(
                cells = GridCells.Fixed(3),
                layoutSpec = QuickNavLayoutSpec.Horizontal()
            ) {
                items(100) { GridItem(it) }
            }
        }
    }
}
