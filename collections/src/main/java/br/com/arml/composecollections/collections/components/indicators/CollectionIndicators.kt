/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.components.indicators

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.collections.R

/**
 * A linear progress indicator that can be placed at the top or bottom of a collection.
 */
@Composable
fun CollectionLinearIndicator(
    progress: Float,
    isHorizontal: Boolean,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier
            .then(if (isHorizontal) Modifier.height(2.dp).fillMaxWidth() else Modifier.width(2.dp).fillMaxHeight())
            .background(color.copy(alpha = 0.2f))
            .testTag(stringResource(R.string.quickNav_indicator_testTag))
    ) {
        Box(
            modifier = Modifier
                .then(if (isHorizontal) Modifier.fillMaxHeight().fillMaxWidth(progress) else Modifier.fillMaxWidth().fillMaxHeight(progress))
                .background(color)
        )
    }
}

/**
 * A modern, thin scrollbar that appears only during scrolling.
 */
@Composable
fun CollectionScrollbar(
    progress: Float,
    isScrolling: Boolean,
    isHorizontal: Boolean,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
) {
    AnimatedVisibility(
        visible = isScrolling,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .then(
                    if (isHorizontal) {
                        Modifier.fillMaxWidth().height(4.dp).padding(horizontal = 16.dp)
                    } else {
                        Modifier.fillMaxHeight().width(4.dp).padding(vertical = 16.dp)
                    }
                )
                .clip(CircleShape)
                .background(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .align(if (isHorizontal) Alignment.CenterEnd else Alignment.BottomCenter)
                    .then(
                        if (isHorizontal) {
                            Modifier.fillMaxHeight().fillMaxWidth(1.0f - progress)
                        } else {
                            Modifier.fillMaxWidth().fillMaxHeight(1.0f - progress)
                        }
                    )
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

/**
 * A textual counter showing current page vs total pages.
 *
 * **Note:** This works correctly only when the items in the collection have the same size.
 */
@Composable
fun CollectionPageCounter(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$currentPage / $totalPages",
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}
