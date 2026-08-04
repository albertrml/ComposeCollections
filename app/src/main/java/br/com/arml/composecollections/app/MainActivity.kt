/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.scrollables.samples.scrollables.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryApp()
        }
    }
}

enum class Screen {
    Dashboard,
    VerticalPagedList,
    HorizontalPagedList,
    HorizontalEdgedList,
    VerticalOverlayList,
    StickyHeaderList,
    VerticalEdgedGrid,
    HorizontalPagedGrid,
    VerticalStaggeredPinterest,
    HorizontalStaggeredGrid,
    ThemedSample
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryApp() {
    var currentScreen by remember { mutableStateOf(Screen.Dashboard) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = when (currentScreen) {
                                Screen.Dashboard -> "QuickNav Gallery"
                                else -> currentScreen.name.replace("([a-z])([A-Z])".toRegex(), "$1 $2")
                            }
                        )
                    },
                    navigationIcon = {
                        if (currentScreen != Screen.Dashboard) {
                            IconButton(onClick = { currentScreen = Screen.Dashboard }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                BackHandler(enabled = currentScreen != Screen.Dashboard) {
                    currentScreen = Screen.Dashboard
                }

                when (currentScreen) {
                    Screen.Dashboard -> Dashboard(onNavigate = { currentScreen = it })
                    Screen.VerticalPagedList -> VerticalPagedListSample()
                    Screen.HorizontalPagedList -> HorizontalPagedListSample()
                    Screen.HorizontalEdgedList -> HorizontalEdgedListSample()
                    Screen.VerticalOverlayList -> VerticalOverlayListSample()
                    Screen.StickyHeaderList -> StickyHeaderListSample()
                    Screen.VerticalEdgedGrid -> VerticalEdgedGridSample()
                    Screen.HorizontalPagedGrid -> HorizontalPagedGridSample()
                    Screen.VerticalStaggeredPinterest -> VerticalStaggeredPinterestSample()
                    Screen.HorizontalStaggeredGrid -> HorizontalStaggeredGridSample()
                    Screen.ThemedSample -> ThemedNavigationSample()
                }
            }
        }
    }
}

@Composable
fun Dashboard(onNavigate: (Screen) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { CategoryHeader("Lists") }
        items(listOf(
            "Vertical Paged List" to Screen.VerticalPagedList,
            "Horizontal Paged List" to Screen.HorizontalPagedList,
            "Horizontal Edged List" to Screen.HorizontalEdgedList,
            "Vertical Overlay List" to Screen.VerticalOverlayList,
            "Sticky Header List" to Screen.StickyHeaderList
        )) { (label, screen) ->
            SampleItem(label, onClick = { onNavigate(screen) })
        }

        item { CategoryHeader("Grids") }
        items(listOf(
            "Vertical Edged Grid" to Screen.VerticalEdgedGrid,
            "Horizontal Paged Grid" to Screen.HorizontalPagedGrid,
            "Vertical Staggered Pinterest" to Screen.VerticalStaggeredPinterest,
            "Horizontal Staggered Grid" to Screen.HorizontalStaggeredGrid
        )) { (label, screen) ->
            SampleItem(label, onClick = { onNavigate(screen) })
        }

        item { CategoryHeader("Customization") }
        item {
            SampleItem("Custom Labels & Icons", onClick = { onNavigate(Screen.ThemedSample) })
        }
    }
}

@Composable
fun CategoryHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SampleItem(label: String, onClick: () -> Unit) {
    Column {
        ListItem(
            headlineContent = { Text(label) },
            modifier = Modifier.clickable { onClick() }
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
    }
}
