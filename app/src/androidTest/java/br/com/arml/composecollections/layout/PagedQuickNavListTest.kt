package br.com.arml.composecollections.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arml.composecollections.composecollections.layout.PagedQuickNavList
import br.com.arml.composecollections.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PagedQuickNavListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var pagedQuickNavListTag: String
    private lateinit var pagedQuickNavListUpButtonTag: String
    private lateinit var pagedQuickNavListDownButtonTag: String

    private val items = List(100) { "Item $it" }
    private val shortedList = List(5) { "Item $it" }

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            pagedQuickNavListTag = getString(R.string.pagedQuickNavList_component_testTag)
            pagedQuickNavListUpButtonTag = getString(R.string.pagedQuickNavList_upButton_testTag)
            pagedQuickNavListDownButtonTag = getString(R.string.pagedQuickNavList_downButton_testTag)
        }
    }

    private fun pagedQuickNavListContent(list: List<String> = items) {
        composeTestRule.setContent {
            PagedQuickNavList {
                items(list) { item ->
                    Text(text = item, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

    @Test
    fun pagedQuickNavList_shouldNotShowsButtons_whenAllItemIsOnScreen() {
        pagedQuickNavListContent(shortedList)
        composeTestRule.apply {
            onNodeWithTag(pagedQuickNavListTag).assertExists()
            onNodeWithTag(pagedQuickNavListDownButtonTag).assertIsNotDisplayed()
            onNodeWithTag(pagedQuickNavListUpButtonTag).assertIsNotDisplayed()
            shortedList.forEach { onNodeWithText(it).assertIsDisplayed() }
        }
    }

    @Test
    fun pagedQuickNavList_shouldShowsDownButton_whenAllItemIsNotOnScreen() {
        pagedQuickNavListContent()
        composeTestRule.apply {
            onNodeWithTag(pagedQuickNavListTag).assertExists()
            onNodeWithTag(pagedQuickNavListDownButtonTag).assertIsDisplayed()
            onNodeWithTag(pagedQuickNavListUpButtonTag).assertIsNotDisplayed()
        }
    }

    @Test
    fun pagedQuickNavList_shouldShowUpButton_whenDownButtonIsClicked() {
        pagedQuickNavListContent()
        composeTestRule.apply {
            onNodeWithTag(pagedQuickNavListDownButtonTag).performClick()
            waitForIdle()
            onNodeWithTag(pagedQuickNavListUpButtonTag).assertIsDisplayed()
        }
    }

    @Test
    fun pagedQuickNavList_shouldNavigateBackToStart_whenScrollingDownAndThenUp() {
        val listState = LazyListState()
        composeTestRule.setContent {
            PagedQuickNavList(listState = listState) {
                items(items) { item ->
                    Text(text = item, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        
        composeTestRule.apply {
            // Click down
            onNodeWithTag(pagedQuickNavListDownButtonTag).performClick()
            waitForIdle()
            val indexAfterDown = listState.firstVisibleItemIndex
            assert(indexAfterDown > 0)
            
            // Click up
            onNodeWithTag(pagedQuickNavListUpButtonTag).performClick()
            waitForIdle()
            val indexAfterUp = listState.firstVisibleItemIndex
            
            // Should have moved back
            assert(indexAfterUp < indexAfterDown)
        }
    }
}
