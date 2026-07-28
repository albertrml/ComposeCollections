package br.com.arml.composecollections.composecollections.defaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.arml.composecollections.R

/**
 * Labels and content descriptions for QuickNav buttons.
 */
data class QuickNavLabels(
    val previousLabel: String,
    val previousContentDescription: String,
    val nextLabel: String,
    val nextContentDescription: String
)

object QuickNavLabelDefaults {
    @Composable
    fun edgedLabels() = QuickNavLabels(
        previousLabel = stringResource(R.string.quickNavList_upButton_text),
        previousContentDescription = stringResource(R.string.quickNavList_upButton_contentDescription),
        nextLabel = stringResource(R.string.quickNavList_downButton_text),
        nextContentDescription = stringResource(R.string.quickNavList_downButton_contentDescription)
    )

    @Composable
    fun pagedLabels() = QuickNavLabels(
        previousLabel = stringResource(R.string.pagedQuickNavList_upButton_text),
        previousContentDescription = stringResource(R.string.pagedQuickNavList_upButton_contentDescription),
        nextLabel = stringResource(R.string.pagedQuickNavList_downButton_text),
        nextContentDescription = stringResource(R.string.pagedQuickNavList_downButton_contentDescription)
    )
}
