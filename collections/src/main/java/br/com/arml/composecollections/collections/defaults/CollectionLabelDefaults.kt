/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.defaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import br.com.arml.composecollections.collections.R

@Immutable
data class CollectionLabels(
    val componentTag: String,
    val previousLabel: String,
    val previousContentDescription: String,
    val previousTag: String,
    val nextLabel: String,
    val nextContentDescription: String,
    val nextTag: String
)

object CollectionLabelDefaults {
    @Composable
    fun defaultLabels(
        mode: CollectionMode,
        step: Int = 2
    ) = when (mode) {
        CollectionMode.Edged -> edgedLabels()
        CollectionMode.Paged -> pagedLabels()
        CollectionMode.Stepped -> steppedLabels(step.coerceAtLeast(2))
    }

    @Composable
    fun edgedLabels() = CollectionLabels(
        componentTag = stringResource(R.string.collectionEdgedLabel_component_testTag),
        previousLabel = stringResource(R.string.collectionEdgedLabel_upButton_text),
        previousContentDescription = stringResource(R.string.collectionEdgedLabel_upButton_contentDescription),
        previousTag = stringResource(R.string.collectionEdgedLabel_upButton_testTag),
        nextLabel = stringResource(R.string.collectionEdgedLabel_downButton_text),
        nextContentDescription = stringResource(R.string.collectionEdgedLabel_downButton_contentDescription),
        nextTag = stringResource(R.string.collectionEdgedLabel_downButton_testTag)
    )

    @Composable
    fun pagedLabels() = CollectionLabels(
        componentTag = stringResource(R.string.collectionPagedLabel_component_testTag),
        previousLabel = stringResource(R.string.collectionPagedLabel_upButton_text),
        previousContentDescription = stringResource(R.string.collectionPagedLabel_upButton_contentDescription),
        previousTag = stringResource(R.string.collectionPagedLabel_upButton_testTag),
        nextLabel = stringResource(R.string.collectionPagedLabel_downButton_text),
        nextContentDescription = stringResource(R.string.collectionPagedLabel_downButton_contentDescription),
        nextTag = stringResource(R.string.collectionPagedLabel_downButton_testTag)
    )

    @Composable
    fun steppedLabels(step: Int) = CollectionLabels(
        componentTag = stringResource(R.string.collectionSteppedLabel_component_testTag),
        previousLabel = pluralStringResource(R.plurals.collectionSteppedLabel_upButton_text, step, step),
        previousContentDescription = pluralStringResource(R.plurals.collectionSteppedLabel_upButton_contentDescription, step, step),
        previousTag = stringResource(R.string.collectionSteppedLabel_upButton_testTag),
        nextLabel = pluralStringResource(R.plurals.collectionSteppedLabel_downButton_text, step, step),
        nextContentDescription = pluralStringResource(R.plurals.collectionSteppedLabel_downButton_contentDescription, step, step),
        nextTag = stringResource(R.string.collectionSteppedLabel_downButton_testTag)
    )
}
