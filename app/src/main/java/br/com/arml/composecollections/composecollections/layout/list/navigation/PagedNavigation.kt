package br.com.arml.composecollections.composecollections.layout.list.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.R
import br.com.arml.composecollections.composecollections.components.FastScrollButton
import br.com.arml.composecollections.composecollections.defaults.QuickNavLabels
import br.com.arml.composecollections.composecollections.defaults.QuickNavTheme

@Composable
fun PagedNavigation(
    showScrollToPrevious: Boolean,
    showScrollToNext: Boolean,
    labels: QuickNavLabels,
    onScrollToPrevious: () -> Unit,
    onScrollToNext: () -> Unit
){
    val icons = QuickNavTheme.icons
    val transitions = QuickNavTheme.transitions

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        AnimatedVisibility(
            visible = showScrollToPrevious,
            enter = transitions.enter,
            exit = transitions.exit
        ) {
            FastScrollButton(
                modifier = Modifier
                    .testTag(stringResource(R.string.pagedQuickNavList_upButton_testTag)),
                text = labels.previousLabel,
                icon = icons.upIcon,
                iconContentDescription = labels.previousContentDescription,
                onClick = onScrollToPrevious
            )
        }

        AnimatedVisibility(
            visible = showScrollToNext,
            enter = transitions.enter,
            exit = transitions.exit
        ) {
            FastScrollButton(
                modifier = Modifier
                    .testTag(stringResource(R.string.pagedQuickNavList_downButton_testTag)),
                text = labels.nextLabel,
                icon = icons.downIcon,
                iconContentDescription = labels.nextContentDescription,
                onClick = onScrollToNext
            )
        }
    }
}
