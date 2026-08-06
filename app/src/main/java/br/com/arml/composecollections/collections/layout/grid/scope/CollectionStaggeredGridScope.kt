/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout.grid.scope

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

/**
 * Receiver scope which is used by Collection Staggered Grid components to provide sticky header support.
 */
interface CollectionStaggeredGridScope {
    fun stickyHeader(
        key: Any? = null,
        contentType: Any? = null,
        content: @Composable () -> Unit
    )

    fun item(
        key: Any? = null,
        span: StaggeredGridItemSpan? = null,
        contentType: Any? = null,
        content: @Composable LazyStaggeredGridItemScope.() -> Unit
    )

    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        span: ((index: Int) -> StaggeredGridItemSpan)? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: @Composable LazyStaggeredGridItemScope.(index: Int) -> Unit
    )
}

internal class CollectionStaggeredGridItem(
    val key: Any?,
    val span: StaggeredGridItemSpan?,
    val contentType: Any?,
    val content: @Composable (LazyStaggeredGridItemScope?) -> Unit,
    val isHeader: Boolean = false
)

fun <T> CollectionStaggeredGridScope.items(
    items: List<T>,
    key: ((item: T) -> Any)? = null,
    span: ((item: T) -> StaggeredGridItemSpan)? = null,
    contentType: (item: T) -> Any? = { null },
    itemContent: @Composable LazyStaggeredGridItemScope.(item: T) -> Unit
) {
    items(
        count = items.size,
        key = key?.let { { index -> it(items[index]) } },
        span = span?.let { { index -> it(items[index]) } },
        contentType = { index -> contentType(items[index]) },
        itemContent = { index -> itemContent(items[index]) }
    )
}

internal fun LazyStaggeredGridScope.renderCollectionItems(items: List<CollectionStaggeredGridItem>) {
    items.forEach { gridItem ->
        item(
            key = gridItem.key,
            span = gridItem.span,
            contentType = gridItem.contentType,
            content = {
                Box(modifier = if (gridItem.isHeader) Modifier.semantics { heading() } else Modifier) {
                    gridItem.content(this@item)
                }
            }
        )
    }
}

internal class CollectionStaggeredGridScopeImpl : CollectionStaggeredGridScope {
    val items = mutableListOf<CollectionStaggeredGridItem>()
    val headerIndexes = mutableListOf<Int>()

    override fun stickyHeader(
        key: Any?,
        contentType: Any?,
        content: @Composable () -> Unit
    ) {
        headerIndexes.add(items.size)
        items.add(CollectionStaggeredGridItem(key, StaggeredGridItemSpan.FullLine, contentType, { content() }, isHeader = true))
    }

    override fun item(
        key: Any?,
        span: StaggeredGridItemSpan?,
        contentType: Any?,
        content: @Composable LazyStaggeredGridItemScope.() -> Unit
    ) {
        items.add(CollectionStaggeredGridItem(key, span, contentType, { content(it!!) }))
    }

    override fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        span: ((index: Int) -> StaggeredGridItemSpan)?,
        contentType: (index: Int) -> Any?,
        itemContent: @Composable LazyStaggeredGridItemScope.(index: Int) -> Unit
    ) {
        repeat(count) { index ->
            items.add(
                CollectionStaggeredGridItem(
                    key = key?.invoke(index),
                    span = span?.invoke(index),
                    contentType = contentType(index),
                    content = { itemContent(it!!, index) }
                )
            )
        }
    }
}
