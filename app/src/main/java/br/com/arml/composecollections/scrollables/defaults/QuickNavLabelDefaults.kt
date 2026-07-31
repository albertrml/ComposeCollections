/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.defaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.arml.composecollections.R

/**
 * Data class representing the labels, accessibility descriptions, and test tags for navigation buttons.
 *
 * Use this class to customize the text displayed on navigation buttons or to provide
 * localized strings.
 *
 * @property previousLabel The text for the "backward" or "up" navigation action.
 * @property previousContentDescription The accessibility text for the previous button.
 * @property previousTag The test tag used to identify the previous button in UI tests.
 * @property nextLabel The text for the "forward" or "down" navigation action.
 * @property nextContentDescription The accessibility text for the next button.
 * @property nextTag The test tag used to identify the next button in UI tests.
 */
data class QuickNavLabels(
    val previousLabel: String,
    val previousContentDescription: String,
    val previousTag: String,
    val nextLabel: String,
    val nextContentDescription: String,
    val nextTag: String
)

/**
 * Provides default [QuickNavLabels] implementations using bundled string resources.
 */
object QuickNavLabelDefaults {
    /**
     * Default labels for "Edged" components (e.g., Start/End or Top/Bottom extremes).
     */
    @Composable
    fun edgedLabels() = QuickNavLabels(
        previousLabel = stringResource(R.string.quickNavList_upButton_text),
        previousContentDescription = stringResource(R.string.quickNavList_upButton_contentDescription),
        previousTag = stringResource(R.string.quickNavList_upButton_testTag),
        nextLabel = stringResource(R.string.quickNavList_downButton_text),
        nextContentDescription = stringResource(R.string.quickNavList_downButton_contentDescription),
        nextTag = stringResource(R.string.quickNavList_downButton_testTag)
    )

    /**
     * Default labels for "Paged" components (e.g., Previous Page/Next Page).
     */
    @Composable
    fun pagedLabels() = QuickNavLabels(
        previousLabel = stringResource(R.string.pagedQuickNavList_upButton_text),
        previousContentDescription = stringResource(R.string.pagedQuickNavList_upButton_contentDescription),
        previousTag = stringResource(R.string.pagedQuickNavList_upButton_testTag),
        nextLabel = stringResource(R.string.pagedQuickNavList_downButton_text),
        nextContentDescription = stringResource(R.string.pagedQuickNavList_downButton_contentDescription),
        nextTag = stringResource(R.string.pagedQuickNavList_downButton_testTag)
    )
}
