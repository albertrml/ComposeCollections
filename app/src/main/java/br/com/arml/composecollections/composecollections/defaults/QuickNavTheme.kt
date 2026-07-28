package br.com.arml.composecollections.composecollections.defaults

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Icons used by QuickNav components.
 */
data class QuickNavIcons(
    val upIcon: ImageVector,
    val downIcon: ImageVector
)

object QuickNavIconDefaults {
    val default = QuickNavIcons(
        upIcon = Icons.Filled.KeyboardArrowUp,
        downIcon = Icons.Filled.KeyboardArrowDown
    )
}

/**
 * Transitions used by QuickNav components.
 */
data class QuickNavTransitions(
    val enter: EnterTransition,
    val exit: ExitTransition
)

object QuickNavTransitionDefaults {
    val default = QuickNavTransitions(
        enter = TransitionDefaults.fadeIn,
        exit = TransitionDefaults.fadeOut
    )
}

val LocalQuickNavLabels = staticCompositionLocalOf<QuickNavLabels?> { null }
val LocalQuickNavIcons = staticCompositionLocalOf { QuickNavIconDefaults.default }
val LocalQuickNavTransitions = staticCompositionLocalOf { QuickNavTransitionDefaults.default }

/**
 * Theme for QuickNav components. Allows global configuration of labels, icons, and transitions.
 */
@Composable
fun QuickNavTheme(
    labels: QuickNavLabels? = null,
    icons: QuickNavIcons = QuickNavIconDefaults.default,
    transitions: QuickNavTransitions = QuickNavTransitionDefaults.default,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalQuickNavLabels provides labels,
        LocalQuickNavIcons provides icons,
        LocalQuickNavTransitions provides transitions,
        content = content
    )
}

object QuickNavTheme {
    val labels: QuickNavLabels
        @Composable
        get() = LocalQuickNavLabels.current ?: QuickNavLabelDefaults.edgedLabels()

    val icons: QuickNavIcons
        @Composable
        get() = LocalQuickNavIcons.current

    val transitions: QuickNavTransitions
        @Composable
        get() = LocalQuickNavTransitions.current
}
