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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.layout.pager.CollectionEdgedPager
import br.com.arml.composecollections.collections.layout.pager.CollectionPagedPager
import br.com.arml.composecollections.collections.layout.pager.CollectionPager
import br.com.arml.composecollections.collections.layout.pager.CollectionSteppedPager
import br.com.arml.composecollections.collections.state.rememberCollectionPagerState

@Composable
fun BasicHorizontalPagerSample() {
    // Illustrating usage with the simplified pageCount lambda factory
    val state = rememberCollectionPagerState(pageCount = { 10 })

    CollectionPager(
        state = state,
        modifier = Modifier.fillMaxSize(),
        navigationAlignment = CollectionAlignment.Bottom,
        showIndicator = true
    ) { index ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Simple Page #$index",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun BasicVerticalPagerSample() {
    val pageCount = 5
    val state = rememberCollectionPagerState(
        mode = CollectionMode.Paged,
        pageCount = { pageCount }
    )

    Box(Modifier.fillMaxSize()) {
        CollectionPager(
            state = state,
            modifier = Modifier.fillMaxSize(),
            isHorizontal = false,
            isOverlay = true,
            navigationAlignment = CollectionAlignment.Bottom,
            showIndicator = true
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (index % 2 == 0) Color(0xFFFFF3E0) else Color(0xFFF3E5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Page #$index",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun EdgedPagerSample() {
    val pageCount = 10

    Box(Modifier.fillMaxSize()) {
        CollectionEdgedPager(
            pageCount = { pageCount },
            modifier = Modifier.fillMaxSize(),
            navigationAlignment = CollectionAlignment.Bottom,
            showIndicator = true
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (index % 2 == 0) Color(0xFFE3F2FD) else Color(0xFFF1F8E9)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Slide #$index",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun PagedPagerSample() {
    val pageCount = 10

    Box(Modifier.fillMaxSize()) {
        CollectionPagedPager(
            pageCount = { pageCount },
            modifier = Modifier.fillMaxSize(),
            navigationAlignment = CollectionAlignment.Bottom,
            showIndicator = true
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (index % 2 == 0) Color(0xFFE3F2FD) else Color(0xFFF1F8E9)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Slide #$index",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SteppedPagerSample() {
    val pageCount = 20

    Box(Modifier.fillMaxSize()) {
        CollectionSteppedPager(
            pageCount = { pageCount },
            step = 3, // Pula de 3 em 3
            modifier = Modifier.fillMaxSize(),
            navigationAlignment = CollectionAlignment.Bottom,
            showIndicator = true
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (index % 2 == 0) Color(0xFFE1F5FE) else Color(0xFFEDE7F6)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Page #$index",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "(Stepped Navigation: 3 pages per click)",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
