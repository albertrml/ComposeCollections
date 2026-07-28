package br.com.arml.composecollections.composecollections.layout.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.R

@Composable
fun QuickNavList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    navigationTop: @Composable () -> Unit = {},
    navigationBottom: @Composable () -> Unit = {},
    content: LazyListScope.() -> Unit
) {
    Column(
        modifier = modifier.testTag(stringResource(R.string.quickNavList_component_testTag)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        navigationTop()

        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )

        navigationBottom()
    }
}

@Preview(showBackground = true)
@Composable
fun QuickNavListPreview(){
    val elements = List(100){ it }
    QuickNavList(modifier = Modifier.fillMaxWidth()) {
        items(elements){
            Text(it.toString())
        }
    }
}
