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

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

/**
 * Defines the scroll animation preset for navigation actions.
 */
enum class QuickNavAnimationMode {
    /** Standard scroll behavior. */
    Default,
    /** Precise, bouncy-free stop. */
    Snap,
    /** Smooth, elastic rebound feeling. */
    Elastic
}

/**
 * Built-in scroll animation presets for QuickNav components.
 */
object QuickNavAnimationDefaults {
    /** High stiffness spring for a precise, "dry" stop. */
    val Snap: AnimationSpec<Float> = spring(
        stiffness = Spring.StiffnessHigh,
        dampingRatio = Spring.DampingRatioNoBouncy
    )

    /** Medium bouncy spring for a smooth, elastic feeling. */
    val Elastic: AnimationSpec<Float> = spring(
        stiffness = Spring.StiffnessMediumLow,
        dampingRatio = Spring.DampingRatioMediumBouncy
    )
}

fun getQuickNavAnimation(animationMode: QuickNavAnimationMode) = when (animationMode) {
    QuickNavAnimationMode.Snap -> QuickNavAnimationDefaults.Snap
    QuickNavAnimationMode.Elastic -> QuickNavAnimationDefaults.Elastic
    QuickNavAnimationMode.Default -> null
}