package com.whalescale.binit.view.main.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.filters.MediumTest
import com.whalescale.binit.ui.theme.WasteAndroidAppTheme
import com.whalescale.binit.view.main.MainViewModel
import com.whalescale.binit.view.main.screens.home.HomeScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@MediumTest
class MainNavigationKtTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
       composeTestRule.setContent {
            WasteAndroidAppTheme {
                navController = TestNavHostController(LocalContext.current)
                mainViewModel = MainViewModel(FakeBinitRepository())
                HomeScreen(navController = navController, mainViewModel)
            }
        }

        hiltRule.inject()
    }

    @Test
    fun mainScreen_screenIsDisplaying() {
        composeTestRule.onNodeWithTag("test_main").assertIsDisplayed()
    }

    @Test
    fun mainScreen_searchIsDisplaying() {
        composeTestRule.onNodeWithTag("search_view").assertIsDisplayed()
    }

    @Test
    fun mainScreen_suggestionsIsDisplaying() {
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithTag("search_chip").fetchSemanticsNodes().isNotEmpty()
        }


    }

    @Test
    fun mainScreen_garbageCategoriesIsDisplaying() {
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithTag("garbage_type_card").fetchSemanticsNodes()
                .isNotEmpty()
        }
    }
}