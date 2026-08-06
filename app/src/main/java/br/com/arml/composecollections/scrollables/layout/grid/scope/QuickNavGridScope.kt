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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

/**
 * Receiver scope which is used by [br.com.arml.composecollections.scrollables.layout.grid.PagedGrid] and [br.com.arml.composecollections.scrollables.layout.grid.EdgedGrid] to provide sticky header support.
 */
interface QuickNavGridScope {
    /**
     * Adds a sticky header to the grid.
     */
    fun stickyHeader(
        key: Any? = null,
        contentType: Any? = null,
        content: @Composable () -> Unit
    )

    /**
     * Adds a single item to the grid.
     */
    fun item(
        key: Any? = null,
        span: (LazyGridItemSpanScope.() -> GridItemSpan)? = null,
        contentType: Any? = null,
        content: @Composable LazyGridItemScope.() -> Unit
    )

    /**
     * Adds multiple items to the grid.
     */
    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        span: (LazyGridItemSpanScope.(index: Int) -> GridItemSpan)? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: @Composable LazyGridItemScope.(index: Int) -> Unit
    )
}

internal class QuickNavGridItem(
    val key: Any?,
    val span: (LazyGridItemSpanScope.() -> GridItemSpan)?,
    val contentType: Any?,
    val content: @Composable (LazyGridItemScope?) -> Unit,
    val isHeader: Boolean = false
)

/**
 * Extension for [QuickNavGridScope] to add a collection of items.
 */
fun <T> QuickNavGridScope.items(
    items: List<T>,
    key: ((item: T) -> Any)? = null,
    span: (LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
    contentType: (item: T) -> Any? = { null },
    itemContent: @Composable LazyGridItemScope.(item: T) -> Unit
) {
    items(
        count = items.size,
        key = key?.let { { index -> it(items[index]) } },
        span = span?.let { { index -> it(items[index]) } },
        contentType = { index -> contentType(items[index]) },
        itemContent = { index -> itemContent(items[index]) }
    )
}

/**
 * Internal helper to render items from a [QuickNavGridScopeImpl] into a [LazyGridScope].
 */
internal fun LazyGridScope.renderQuickNavItems(items: List<QuickNavGridItem>) {
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

internal class QuickNavGridScopeImpl : QuickNavGridScope {
    val items = mutableListOf<QuickNavGridItem>()
    val headerIndexes = mutableListOf<Int>()

    override fun stickyHeader(
        key: Any?,
        contentType: Any?,
        content: @Composable () -> Unit
    ) {
        headerIndexes.add(items.size)
        items.add(QuickNavGridItem(key, { GridItemSpan(maxLineSpan) }, contentType, { content() }, isHeader = true))
    }

    override fun item(
        key: Any?,
        span: (LazyGridItemSpanScope.() -> GridItemSpan)?,
        contentType: Any?,
        content: @Composable LazyGridItemScope.() -> Unit
    ) {
        items.add(QuickNavGridItem(key, span, contentType, { content(it!!) }))
    }

    override fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        span: (LazyGridItemSpanScope.(index: Int) -> GridItemSpan)?,
        contentType: (index: Int) -> Any?,
        itemContent: @Composable LazyGridItemScope.(index: Int) -> Unit
    ) {
        repeat(count) { index ->
            items.add(
                QuickNavGridItem(
                    key = key?.invoke(index),
                    span = span?.let { { it(index) } },
                    contentType = contentType(index),
                    content = { itemContent(it!!, index) }
                )
            )
        }
    }
}
