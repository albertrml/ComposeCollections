package br.com.arml.composecollections.composecollections.layout.list

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
import br.com.arml.composecollections.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PagedListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var pagedListTag: String
    private lateinit var pagedListPreviousButtonTag: String
    private lateinit var pagedListNextButtonTag: String

    private val items = List(100) { "Item $it" }
    private val shortedList = List(5) { "Item $it" }

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            pagedListTag = getString(R.string.pagedQuickNavList_component_testTag)
            pagedListPreviousButtonTag = getString(R.string.pagedQuickNavList_upButton_testTag)
            pagedListNextButtonTag = getString(R.string.pagedQuickNavList_downButton_testTag)
        }
    }

    private fun pagedListContent(list: List<String> = items) {
        composeTestRule.setContent {
            PagedList {
                items(list) { item ->
                    Text(text = item, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

    @Test
    fun pagedList_shouldNotShowsButtons_whenAllItemIsOnScreen() {
        pagedListContent(shortedList)
        composeTestRule.apply {
            onNodeWithTag(pagedListTag).assertExists()
            onNodeWithTag(pagedListNextButtonTag).assertIsNotDisplayed()
            onNodeWithTag(pagedListPreviousButtonTag).assertIsNotDisplayed()
            shortedList.forEach { onNodeWithText(it).assertIsDisplayed() }
        }
    }

    @Test
    fun pagedList_shouldShowsNextButton_whenAllItemIsNotOnScreen() {
        pagedListContent()
        composeTestRule.apply {
            onNodeWithTag(pagedListTag).assertExists()
            onNodeWithTag(pagedListNextButtonTag).assertIsDisplayed()
            onNodeWithTag(pagedListPreviousButtonTag).assertIsNotDisplayed()
        }
    }

    @Test
    fun pagedList_shouldShowPreviousButton_whenNextButtonIsClicked() {
        pagedListContent()
        composeTestRule.apply {
            onNodeWithTag(pagedListNextButtonTag).performClick()
            waitForIdle()
            onNodeWithTag(pagedListPreviousButtonTag).assertIsDisplayed()
        }
    }

    @Test
    fun pagedList_shouldNavigateBackToStart_whenScrollingDownAndThenUp() {
        val listState = LazyListState()
        composeTestRule.setContent {
            PagedList(listState = listState) {
                items(items) { item ->
                    Text(text = item, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        
        composeTestRule.apply {
            // Click next
            onNodeWithTag(pagedListNextButtonTag).performClick()
            waitForIdle()
            val indexAfterDown = listState.firstVisibleItemIndex
            assert(indexAfterDown > 0)
            
            // Click previous
            onNodeWithTag(pagedListPreviousButtonTag).performClick()
            waitForIdle()
            val indexAfterUp = listState.firstVisibleItemIndex
            
            // Should have moved back
            assert(indexAfterUp < indexAfterDown)
        }
    }
}
