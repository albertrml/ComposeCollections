/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.layout.grid.scope

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable

/**
 * Receiver scope which is used by [br.com.arml.composecollections.scrollables.layout.grid.PagedStaggeredGrid] and [br.com.arml.composecollections.scrollables.layout.grid.EdgedStaggeredGrid] to provide sticky header support.
 */
interface QuickNavStaggeredGridScope {
    /**
     * Adds a sticky header to the staggered grid.
     */
    fun stickyHeader(
        key: Any? = null,
        contentType: Any? = null,
        content: @Composable () -> Unit
    )

    /**
     * Adds a single item to the staggered grid.
     */
    fun item(
        key: Any? = null,
        span: StaggeredGridItemSpan? = null,
        contentType: Any? = null,
        content: @Composable LazyStaggeredGridItemScope.() -> Unit
    )

    /**
     * Adds multiple items to the staggered grid.
     */
    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        span: ((index: Int) -> StaggeredGridItemSpan)? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: @Composable LazyStaggeredGridItemScope.(index: Int) -> Unit
    )
}

internal class QuickNavStaggeredGridItem(
    val key: Any?,
    val span: StaggeredGridItemSpan?,
    val contentType: Any?,
    val content: @Composable (LazyStaggeredGridItemScope?) -> Unit,
    val isHeader: Boolean = false
)

/**
 * Extension for [QuickNavStaggeredGridScope] to add a collection of items.
 */
fun <T> QuickNavStaggeredGridScope.items(
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

internal class QuickNavStaggeredGridScopeImpl : QuickNavStaggeredGridScope {
    val items = mutableListOf<QuickNavStaggeredGridItem>()
    val headerIndexes = mutableListOf<Int>()

    override fun stickyHeader(
        key: Any?,
        contentType: Any?,
        content: @Composable () -> Unit
    ) {
        headerIndexes.add(items.size)
        items.add(QuickNavStaggeredGridItem(key, StaggeredGridItemSpan.FullLine, contentType, { content() }, isHeader = true))
    }

    override fun item(
        key: Any?,
        span: StaggeredGridItemSpan?,
        contentType: Any?,
        content: @Composable LazyStaggeredGridItemScope.() -> Unit
    ) {
        items.add(QuickNavStaggeredGridItem(key, span, contentType, { content(it!!) }))
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
                QuickNavStaggeredGridItem(
                    key = key?.invoke(index),
                    span = span?.invoke(index),
                    contentType = contentType(index),
                    content = { itemContent(it!!, index) }
                )
            )
        }
    }
}
