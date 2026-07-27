package br.com.arml.composecollections.composecollections.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.R
import br.com.arml.composecollections.composecollections.components.FastScrollButton
import kotlinx.coroutines.launch

@Composable
fun PagedQuickNavList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: LazyListScope.() -> Unit
) {
    val scope = rememberCoroutineScope()

    val showScrollToTop by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    val showScrollToBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            if (totalItems == 0) return@derivedStateOf false
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem < totalItems - 1
        }
    }

    Column(
        modifier = modifier.testTag(stringResource(R.string.pagedQuickNavList_component_testTag)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (showScrollToTop) {
            FastScrollButton(
                modifier = Modifier
                    .testTag(stringResource(R.string.pagedQuickNavList_upButton_testTag)),
                text = stringResource(R.string.pagedQuickNavList_upButton_text),
                icon = Icons.Filled.KeyboardArrowUp,
                iconContentDescription = stringResource(
                    R.string.pagedQuickNavList_upButton_contentDescription
                ),
                onClick = {
                    scope.launch {
                        val visibleItemsCount = listState.layoutInfo.visibleItemsInfo.size
                        val targetIndex = (listState.firstVisibleItemIndex - visibleItemsCount).coerceAtLeast(0)
                        listState.animateScrollToItem(targetIndex)
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )

        if (showScrollToBottom) {
            FastScrollButton(
                modifier = Modifier
                    .testTag(stringResource(R.string.pagedQuickNavList_downButton_testTag)),
                text = stringResource(R.string.pagedQuickNavList_downButton_text),
                icon = Icons.Filled.KeyboardArrowDown,
                iconContentDescription = stringResource(
                    R.string.pagedQuickNavList_downButton_contentDescription
                ),
                onClick = {
                    scope.launch {
                        val visibleItemsCount = listState.layoutInfo.visibleItemsInfo.size
                        val targetIndex = (listState.firstVisibleItemIndex + visibleItemsCount)
                            .coerceAtMost(listState.layoutInfo.totalItemsCount - 1)
                        if (targetIndex >= 0) {
                            listState.animateScrollToItem(targetIndex)
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PagedQuickNavListPreview(){
    val elements = List(100){ it }
    PagedQuickNavList(modifier = Modifier.fillMaxWidth()) {
        items(elements){
            Text(it.toString())
        }
    }
}
