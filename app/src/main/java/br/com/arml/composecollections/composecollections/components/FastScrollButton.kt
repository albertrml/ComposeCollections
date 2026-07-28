package br.com.arml.composecollections.composecollections.components

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

@Composable
fun FastScrollButton(
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
fun FastScrollButtonPreview(){
    FastScrollButton(
        text = "Jump to Top",
        icon = Icons.Filled.KeyboardArrowUp,
        iconContentDescription = stringResource(
            R.string.quickNavList_upButton_contentDescription
        )
    )
}