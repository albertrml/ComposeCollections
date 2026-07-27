package br.com.arml.composecollections.layout

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
import br.com.arml.composecollections.composecollections.layout.QuickNavList
import br.com.arml.composecollections.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuickNavListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var quickNavListTag: String
    private lateinit var quickNavListUpButtonTag: String
    private lateinit var quickNavListDownButtonTag: String

    private val items = List(100) { "Item $it" }
    private val shortedList = List(20) { "Item $it" }
    private val startItem = items.first()
    private val endItem = items.last()

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            quickNavListTag = getString(R.string.quickNavList_component_testTag)
            quickNavListUpButtonTag = getString(R.string.quickNavList_upButton_testTag)
            quickNavListDownButtonTag = getString(R.string.quickNavList_downButton_testTag)
        }
    }

    private fun quickNavListContent(list: List<String> = items) {
        composeTestRule.setContent {
            QuickNavList { items(list) { item -> Text(item) } }
        }
    }

    @Test
    fun quickNavList_shouldNotShowsButtons_whenAllItemIsOnScreen() {
        quickNavListContent(shortedList)
        composeTestRule.apply {
            onNodeWithTag(quickNavListTag).assertExists()
            onNodeWithTag(quickNavListDownButtonTag).assertIsNotDisplayed()
            onNodeWithTag(quickNavListUpButtonTag).assertIsNotDisplayed()
            shortedList.forEach { onNodeWithText(it).assertIsDisplayed() }
        }
    }

    @Test
    fun quickNavList_shouldShowsDownButton_whenAllItemIsNotOnScreen() {
        quickNavListContent()
        composeTestRule.apply {
            onNodeWithTag(quickNavListTag).assertExists()
            onNodeWithTag(quickNavListDownButtonTag).assertIsDisplayed()
            onNodeWithTag(quickNavListUpButtonTag).assertIsNotDisplayed()
        }
    }

    @Test
    fun quickNavList_shouldShowsLastItemAndUpButton_whenListIsAtStartAndDownButtonIsClicked() {
        quickNavListContent()
        composeTestRule.apply {
            onNodeWithTag(quickNavListDownButtonTag).performClick()
            waitForIdle()
            onNodeWithText(endItem).assertIsDisplayed()
            onNodeWithTag(quickNavListUpButtonTag).assertIsDisplayed()
        }
    }

    @Test
    fun quickNavList_shouldShowFirstItemAndDownButton_whenListIsAtTheEndAndUpButtonIsClicked() {
        quickNavListContent()
        composeTestRule.apply {
            onNodeWithTag(quickNavListDownButtonTag).performClick()
            waitForIdle()
            onNodeWithText(endItem).assertIsDisplayed()
            onNodeWithTag(quickNavListUpButtonTag).performClick()
            waitForIdle()
            onNodeWithTag(quickNavListDownButtonTag).assertIsDisplayed()
            onNodeWithText(startItem).assertIsDisplayed()
        }
    }

}
