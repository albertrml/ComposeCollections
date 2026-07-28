package br.com.arml.composecollections.composecollections.layout.list

import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
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
class EdgedListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var edgedListTag: String
    private lateinit var edgedListStartButtonTag: String
    private lateinit var edgedListEndButtonTag: String

    private val items = List(100) { "Item $it" }
    private val shortedList = List(20) { "Item $it" }
    private val startItem = items.first()
    private val endItem = items.last()

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            edgedListTag = getString(R.string.quickNavList_component_testTag)
            edgedListStartButtonTag = getString(R.string.quickNavList_upButton_testTag)
            edgedListEndButtonTag = getString(R.string.quickNavList_downButton_testTag)
        }
    }

    private fun edgedListContent(list: List<String> = items) {
        composeTestRule.setContent {
            EdgedList { items(list) { item -> Text(item) } }
        }
    }

    @Test
    fun edgedList_shouldNotShowsButtons_whenAllItemIsOnScreen() {
        edgedListContent(shortedList)
        composeTestRule.apply {
            onNodeWithTag(edgedListTag).assertExists()
            onNodeWithTag(edgedListEndButtonTag).assertIsNotDisplayed()
            onNodeWithTag(edgedListStartButtonTag).assertIsNotDisplayed()
            shortedList.forEach { onNodeWithText(it).assertIsDisplayed() }
        }
    }

    @Test
    fun edgedList_shouldShowsEndButton_whenAllItemIsNotOnScreen() {
        edgedListContent()
        composeTestRule.apply {
            onNodeWithTag(edgedListTag).assertExists()
            onNodeWithTag(edgedListEndButtonTag).assertIsDisplayed()
            onNodeWithTag(edgedListStartButtonTag).assertIsNotDisplayed()
        }
    }

    @Test
    fun edgedList_shouldShowsLastItemAndStartButton_whenListIsAtStartAndEndButtonIsClicked() {
        edgedListContent()
        composeTestRule.apply {
            onNodeWithTag(edgedListEndButtonTag).performClick()
            waitForIdle()
            onNodeWithText(endItem).assertIsDisplayed()
            onNodeWithTag(edgedListStartButtonTag).assertIsDisplayed()
        }
    }

    @Test
    fun edgedList_shouldShowFirstItemAndEndButton_whenListIsAtTheEndAndStartButtonIsClicked() {
        edgedListContent()
        composeTestRule.apply {
            onNodeWithTag(edgedListEndButtonTag).performClick()
            waitForIdle()
            onNodeWithText(endItem).assertIsDisplayed()
            onNodeWithTag(edgedListStartButtonTag).performClick()
            waitForIdle()
            onNodeWithTag(edgedListEndButtonTag).assertIsDisplayed()
            onNodeWithText(startItem).assertIsDisplayed()
        }
    }

}
