package com.ndteam.wasteandroidapp.view.game.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.MainBlue
import com.ndteam.wasteandroidapp.utils.NavigationUtils.NAVIGATE_BACK
import com.ndteam.wasteandroidapp.utils.NavigationUtils.NAVIGATE_TO_DND
import com.ndteam.wasteandroidapp.utils.NavigationUtils.NAVIGATE_TO_QUIZ
import com.ndteam.wasteandroidapp.view.main.screens.main.TextTitleMain
import com.ndteam.wasteandroidapp.view.main.screens.search.ToolbarPlanIconAndTitle

@Composable
fun GamePickerScreen(navigate: (String) -> Unit) {
    GamePickerScreenContent(navigate = navigate)
}

@Composable
fun GamePickerScreenContent(navigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("test_game_picker")
            .background(Color.White)

    ) {
        ToolbarPlanIconAndTitle(listType = "") {
            navigate(NAVIGATE_BACK)
        }

        TextTitleMain(title = stringResource(R.string.drag_and_drop))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(horizontal = 16.dp)
            .background(color = MainBlue, shape = RoundedCornerShape(16.dp))
            .clickable {
                navigate(NAVIGATE_TO_DND)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_game_placeholder),
                contentDescription = "Game placeholder",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )
        }

        TextTitleMain(title = stringResource(R.string.quiz))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(horizontal = 16.dp)
            .background(color = MainBlue, shape = RoundedCornerShape(16.dp))
            .clickable {
                navigate(NAVIGATE_TO_QUIZ)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_quiz_game_placeholder),
                contentDescription = "Game placeholder",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        BannerAdView()
    }
}

@Preview
@Composable
fun GamePickerScreenContent_Preview() {
    GamePickerScreenContent(navigate = {

    })
}