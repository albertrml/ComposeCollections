package br.com.arml.composecollections.composecollections.layout.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.composecollections.defaults.QuickNavLabels
import br.com.arml.composecollections.composecollections.defaults.QuickNavTheme
import br.com.arml.composecollections.composecollections.layout.list.navigation.EdgedNavigation
import kotlinx.coroutines.launch

@Composable
fun EdgedList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    labels: QuickNavLabels = QuickNavTheme.labels,
    content: LazyListScope.() -> Unit
) {
    val scope = rememberCoroutineScope()

    val showScrollToStart by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    val showScrollToEnd by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            if (totalItems == 0) return@derivedStateOf false
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem < totalItems - 1
        }
    }

    QuickNavList(
        modifier = modifier,
        listState = listState,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        navigationBottom = {
            EdgedNavigation(
                showScrollToStart = showScrollToStart,
                showScrollToEnd = showScrollToEnd,
                labels = labels,
                onScrollToStart = { scope.launch { listState.animateScrollToItem(0) } },
                onScrollToEnd = {
                    scope.launch {
                        val lastItem = listState.layoutInfo.totalItemsCount - 1
                        if (lastItem >= 0) listState.animateScrollToItem(lastItem)
                    }
                }
            )
        },
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun EdgedListPreview(){
    val elements = List(100){ it }
    EdgedList(modifier = Modifier.fillMaxWidth()) {
        items(elements){
            Text(it.toString())
        }
    }
}