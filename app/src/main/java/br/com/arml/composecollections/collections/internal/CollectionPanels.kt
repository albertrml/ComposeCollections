/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.internal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import br.com.arml.composecollections.collections.components.CollectionButton
import br.com.arml.composecollections.collections.defaults.CollectionTheme

@Composable
internal fun HorizontalPanelNavigation(
    modifier: Modifier = Modifier,
    isHorizontal: Boolean = false,
    showScrollToBackward: () -> Boolean,
    showScrollToForward: () -> Boolean,
    onScrollToBackward: () -> Unit,
    onScrollToForward: () -> Unit
){
    val icons = CollectionTheme.icons
    val transitions = CollectionTheme.transitions
    val labels = CollectionTheme.labels
    val dimensions = CollectionTheme.dimensions

    val backwardIcon = if (isHorizontal) icons.left else icons.up
    val forwardIcon = if (isHorizontal) icons.right else icons.down

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensions.buttonSpacing)
    ) {
        CollectionNavigation(
            modifier = Modifier.testTag(labels.previousTag),
            enterTransition = transitions.enter,
            exitTransition = transitions.exit,
            isVisible = showScrollToBackward,
            textButton = labels.previousLabel,
            iconButton = backwardIcon,
            iconContentDescription = labels.previousContentDescription,
            onClickToScroll = onScrollToBackward,
        )

        CollectionNavigation(
            modifier = Modifier.testTag(labels.nextTag),
            enterTransition = transitions.enter,
            exitTransition = transitions.exit,
            isVisible = showScrollToForward,
            textButton = labels.nextLabel,
            iconButton = forwardIcon,
            iconContentDescription = labels.nextContentDescription,
            onClickToScroll = onScrollToForward,
        )
    }
}

@Composable
internal fun VerticalPanelNavigation(
    modifier: Modifier = Modifier,
    isHorizontal: Boolean = false,
    showScrollToBackward: () -> Boolean,
    showScrollToForward: () -> Boolean,
    onScrollToBackward: () -> Unit,
    onScrollToForward: () -> Unit
){
    val icons = CollectionTheme.icons
    val transitions = CollectionTheme.transitions
    val labels = CollectionTheme.labels
    val dimensions = CollectionTheme.dimensions

    val backwardIcon = if (isHorizontal) icons.left else icons.up
    val forwardIcon = if (isHorizontal) icons.right else icons.down

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.buttonSpacing)
    ) {
        CollectionNavigation(
            modifier = Modifier.testTag(labels.previousTag),
            enterTransition = transitions.enter,
            exitTransition = transitions.exit,
            isVisible = showScrollToBackward,
            textButton = labels.previousLabel,
            iconButton = backwardIcon,
            iconContentDescription = labels.previousContentDescription,
            onClickToScroll = onScrollToBackward,
        )

        CollectionNavigation(
            modifier = Modifier.testTag(labels.nextTag),
            enterTransition = transitions.enter,
            exitTransition = transitions.exit,
            isVisible = showScrollToForward,
            textButton = labels.nextLabel,
            iconButton = forwardIcon,
            iconContentDescription = labels.nextContentDescription,
            onClickToScroll = onScrollToForward,
        )
    }
}

@Composable
internal fun SinglePanelNavigation(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    contentDescription: String,
    testTag: String,
    showScrollButton: () -> Boolean,
    onScrollTo: () -> Unit,
){
    CollectionNavigation(
        modifier = modifier.testTag(testTag),
        textButton = label,
        iconButton = icon,
        iconContentDescription = contentDescription,
        isVisible = showScrollButton,
        onClickToScroll = onScrollTo,
    )
}

@Composable
internal fun CollectionNavigation(
    modifier: Modifier = Modifier,
    enterTransition: EnterTransition = CollectionTheme.transitions.enter,
    exitTransition: ExitTransition = CollectionTheme.transitions.exit,
    textButton: String,
    iconButton: ImageVector,
    iconContentDescription: String,
    isVisible: () -> Boolean,
    onClickToScroll: () -> Unit,
){
    AnimatedVisibility(
        visible = isVisible(),
        enter = enterTransition,
        exit = exitTransition
    ) {
        CollectionButton(
            modifier = modifier,
            text = textButton,
            icon = iconButton,
            iconContentDescription = iconContentDescription,
            onClick = onClickToScroll
        )
    }
}
