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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.abs

/**
 * A pagination indicator that uses dots with a sliding window for scalability.
 */
@Composable
fun CollectionDotIndicator(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.outlineVariant,
    maxVisibleDots: Int = 7
) {
    if (totalPages <= 1) return

    val actualCurrentPage = (currentPage - 1).coerceIn(0, totalPages - 1)
    
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Calculate the range of dots to show
        val half = maxVisibleDots / 2
        var start = actualCurrentPage - half
        var end = actualCurrentPage + half
        
        if (start < 0) {
            end += abs(start)
            start = 0
        }
        if (end >= totalPages) {
            start -= (end - totalPages + 1)
            end = totalPages - 1
        }
        start = start.coerceAtLeast(0)
        
        for (i in start..end) {
            val isActive = i == actualCurrentPage
            val distance = abs(i - actualCurrentPage)
            
            // Dynamic sizing for sliding effect
            val size by animateDpAsState(
                targetValue = when {
                    isActive -> 10.dp
                    distance == 1 -> 8.dp
                    distance == 2 -> 6.dp
                    else -> 4.dp
                },
                label = "dotSize"
            )
            
            val color by animateColorAsState(
                targetValue = if (isActive) activeColor else inactiveColor,
                label = "dotColor"
            )

            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
