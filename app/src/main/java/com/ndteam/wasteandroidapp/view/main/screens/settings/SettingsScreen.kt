package com.ndteam.wasteandroidapp.view.main.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.DividerColor
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.view.main.screens.drop_locations.DropOffLocationsScreen
import com.ndteam.wasteandroidapp.view.main.screens.main.BottomNavItem
import com.ndteam.wasteandroidapp.view.main.screens.main.HomeScreen
import com.ndteam.wasteandroidapp.view.main.screens.schedule.ScheduleScreen

sealed class SettingsNavItem(var route: String) {

    object Settings : SettingsNavItem("settings")
    object Invite : SettingsNavItem("invite")
    object Feedback: SettingsNavItem("feedback")
    object ContactUs: SettingsNavItem("contact_us")
    object AboutUs: SettingsNavItem("about_us")
    object RateTheApp: SettingsNavItem("rate_the_app")
    object ReportProblem: SettingsNavItem("report_a_report")

}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = SettingsNavItem.Settings.route) {
        composable(SettingsNavItem.Settings.route) {
            SettingsScreenContent(navController)
        }
        composable(SettingsNavItem.Invite.route) {
            InviteFriendsScreen {
                navController.popBackStack()
            }
        }
        composable(SettingsNavItem.Feedback.route) {
            FeedbackScreen {
                navController.popBackStack()
            }
        }
        composable(SettingsNavItem.ContactUs.route) {
            ContactUsScreen {
                navController.popBackStack()
            }
        }
        composable(SettingsNavItem.AboutUs.route) {
            AboutUsScreen {
                navController.popBackStack()
            }
        }
        composable(SettingsNavItem.RateTheApp.route) {

        }
        composable(SettingsNavItem.ReportProblem.route) {

        }
    }
}
@Composable
fun SettingsScreen() {

    val navController = rememberNavController()
    NavigationGraph(navController = navController)

    /*
        navController.navigate(route) {

            navController.graph.startDestinationRoute?.let { screenRoute ->
                popUpTo(screenRoute) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
     */

}

@Composable
fun SettingsScreenContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_binit_logo),
            contentDescription = "App logo",
            modifier = Modifier
                .size(90.dp, 50.dp)
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        SettingsMenuItem(
            text = stringResource(R.string.invite_friends),
            onClick = {
                navController.navigate(SettingsNavItem.Invite.route)
            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.feedback),
            onClick = {
                navController.navigate(SettingsNavItem.Feedback.route)
            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.contact_us),
            onClick = {
                navController.navigate(SettingsNavItem.ContactUs.route)
            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.about_us),
            onClick = {
                navController.navigate(SettingsNavItem.AboutUs.route)
            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.rate_the_app),
            onClick = {

            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.report_a_problem),
            onClick = {

            }
        )
    }
}

@Composable
fun SettingsMenuItem(text: String, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                color = IconsDark
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_arrow_menu),
                contentDescription = "Arrow menu",
                modifier = Modifier.size(20.dp, 20.dp)
            )
        }

        Divider(
            color = DividerColor
        )
    }
}

@Preview
@Composable
fun SettingsScreen_Preview() {
    val navController = rememberNavController()


    SettingsScreenContent(navController)
}