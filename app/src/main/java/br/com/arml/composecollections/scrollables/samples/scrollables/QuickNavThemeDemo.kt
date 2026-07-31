package br.com.arml.composecollections.scrollables.samples.scrollables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.composecollections.scrollables.defaults.QuickNavIconDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavLabelDefaults
import br.com.arml.composecollections.scrollables.defaults.QuickNavTheme
import br.com.arml.composecollections.scrollables.layout.list.EdgedList

@Preview(showBackground = true)
@Composable
fun QuickNavThemeDemo() {
    val customLabels = QuickNavLabelDefaults.edgedLabels().copy(
        previousLabel = "INÍCIO",
        nextLabel = "FIM"
    )
    val customIcons = QuickNavIconDefaults.default.copy(
        up = Icons.Filled.ArrowUpward,
        down = Icons.Filled.ArrowDownward
    )

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            QuickNavTheme(
                labels = customLabels,
                icons = customIcons
            ) {
                EdgedList {
                    items(List(100) { "Elemento $it" }) {
                        Text(it)
                    }
                }
            }
        }
    }
}
