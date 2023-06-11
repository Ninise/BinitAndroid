package com.ndteam.wasteandroidapp.view.main.screens.settings

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
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
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.android.play.core.review.testing.FakeReviewManager
import com.ndteam.wasteandroidapp.App.Companion.context
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
            ReportProblemScreen {
                navController.popBackStack()
            }
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

    val activity = LocalContext.current as Activity

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

                // todo test on a internal test track then switch to regular

//                val manager = ReviewManagerFactory.create(context)

                val manager = FakeReviewManager(activity)

                val request = manager.requestReviewFlow()
                request.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // We got the ReviewInfo object
                        val reviewInfo = task.result

                        val flow = manager.launchReviewFlow(activity, reviewInfo)
                        flow.addOnCompleteListener { _ ->
                            // The flow has finished. The API does not indicate whether the user
                            // reviewed or not, or even whether the review dialog was shown. Thus, no
                            // matter the result, we continue our app flow.
                        }
                    } else {
                        // There was some problem, log or handle the error code.
                        @ReviewErrorCode val reviewErrorCode = (task.getException() as ReviewException).errorCode
                    }
                }


            }
        )

        SettingsMenuItem(
            text = stringResource(R.string.report_a_problem),
            onClick = {
                navController.navigate(SettingsNavItem.ReportProblem.route)
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