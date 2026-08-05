package br.com.arml.composecollections.scrollables.defaults

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

/**
 * Standard animation specifications used for navigation button visibility.
 */
object TransitionDefaults {

    /** Default fade-in combined with a vertical slide-in from the bottom. */
    val fadeIn = fadeIn(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    ) + slideInVertically(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    /** Default fade-out combined with a vertical slide-out to the bottom. */
    val fadeOut = fadeOut(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    ) + slideOutVertically(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
}