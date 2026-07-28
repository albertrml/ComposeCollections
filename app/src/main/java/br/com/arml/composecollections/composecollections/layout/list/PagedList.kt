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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.R
import br.com.arml.composecollections.composecollections.defaults.LocalQuickNavLabels
import br.com.arml.composecollections.composecollections.defaults.QuickNavLabels
import br.com.arml.composecollections.composecollections.defaults.QuickNavLabelDefaults
import br.com.arml.composecollections.composecollections.layout.list.navigation.PagedNavigation
import kotlinx.coroutines.launch

@Composable
fun PagedList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    labels: QuickNavLabels = LocalQuickNavLabels.current ?: QuickNavLabelDefaults.pagedLabels(),
    content: LazyListScope.() -> Unit
) {
    val scope = rememberCoroutineScope()

    val showScrollToPrevious by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    val showScrollToNext by remember {
        derivedStateOf {
            with(listState.layoutInfo){
                if (totalItemsCount == 0) return@derivedStateOf false
                val lastVisibleItem = visibleItemsInfo.lastOrNull()?.index ?: 0
                lastVisibleItem < totalItemsCount - visibleItemsInfo.size/2
            }
        }
    }

    QuickNavList(
        modifier = modifier.testTag(stringResource(R.string.pagedQuickNavList_component_testTag)),
        listState = listState,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        navigationBottom = {
            PagedNavigation(
                showScrollToPrevious = showScrollToPrevious,
                showScrollToNext = showScrollToNext,
                labels = labels,
                onScrollToPrevious = {
                    scope.launch {
                        val visibleItemsCount = listState.layoutInfo.visibleItemsInfo.size
                        val targetIndex = (listState.firstVisibleItemIndex - visibleItemsCount)
                            .coerceAtLeast(0)
                        listState.animateScrollToItem(targetIndex)
                    }
                },
                onScrollToNext = {
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
        },
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun PagedListPreview(){
    val elements = List(100){ it }
    PagedList(modifier = Modifier.fillMaxWidth()) {
        items(elements){
            Text(it.toString())
        }
    }
}
