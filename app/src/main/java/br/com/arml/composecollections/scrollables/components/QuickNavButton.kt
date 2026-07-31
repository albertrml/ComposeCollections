/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.composecollections.R

/**
 * A standardized button used for quick navigation actions.
 *
 * This button displays an icon and a text label, styled according to the application's
 * Material Theme. It is designed to be used within navigation panels to trigger
 * scroll actions.
 *
 * @param modifier The modifier to be applied to the button.
 * @param text The text label to be displayed next to the icon.
 * @param icon The [ImageVector] to be displayed as the button's icon.
 * @param iconContentDescription The accessibility description for the icon.
 * @param onClick The callback to be invoked when the button is clicked.
 */
@Composable
fun QuickNavButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    iconContentDescription: String? = null,
    onClick: () -> Unit = {},
){
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconContentDescription
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun QuickNavButtonPreview(){
    QuickNavButton(
        text = "Jump to Top",
        icon = Icons.Filled.KeyboardArrowUp,
        iconContentDescription = stringResource(
            R.string.quickNavList_upButton_contentDescription
        )
    )
}
