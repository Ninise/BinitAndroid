package com.ndteam.wasteandroidapp.view.main.screens.settings

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.ui.theme.IconsDark
import com.ndteam.wasteandroidapp.ui.theme.Inter
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.view.custom_views.DefaultButton
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun InviteFriendsScreen(navBack: () -> Unit) {
    InviteFriendsScreenContent(navBack)
}

@Composable
fun InviteFriendsScreenContent(navBack: () -> Unit) {

    val scope = rememberCoroutineScope()

    val offsetY = remember { Animatable(0f) }
    val offsetX = remember { Animatable(0f) }

    val offsetPersonX = remember { Animatable(0f) }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {

        offsetX.snapTo(25f)
        offsetPersonX.snapTo(20f)
    })


    fun dropBanana() {
        scope.launch {
            launch {
                offsetY.animateTo(
                    targetValue = 200f,
                    animationSpec = tween(durationMillis = 3_000)
                )
            }
            offsetX.animateTo(
                targetValue = 140f,
                animationSpec = tween(durationMillis = 1_000)
            )
        }
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        ToolbarWithTitle(stringResource(id = R.string.invite_friends)) {
            navBack()
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.height(180.dp).padding(top = 10.dp)
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_game_item_banana),
                    contentDescription = "Banana",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .offset {
                            IntOffset(
                                offsetX.value.roundToInt(),
                                offsetY.value.roundToInt()
                            )
                        }
                        .padding(bottom = 15.dp)
                        .size(24.dp),
                    contentScale = ContentScale.Fit
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_invite_person_image),
                    contentDescription = "Guy bin",
                    modifier = Modifier
                        .padding(top = 6.dp, start = 6.dp, end = 6.dp)
                        .height(180.dp)
                        .offset {
                            IntOffset(
                                offsetPersonX.value.roundToInt(),
                                0
                            )
                        }
                    ,
                    contentScale = ContentScale.Fit
                )



            }


            Image(
                painter = painterResource(id = R.drawable.ic_def_garbage_bin),
                contentDescription = "Garbage bin",
                modifier = Modifier
                    .padding(end = 6.dp, bottom = 5.dp)
                    .width(70.dp)
                    .height(70.dp)
                    .align(alignment = Alignment.Bottom),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = stringResource(R.string.sub_invite_sub),
            color = IconsDark,
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = 40.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )

        DefaultButton(text = stringResource(id = R.string.share_with_friends), icon = R.drawable.ic_invite, modifier = Modifier.padding(horizontal = 40.dp, vertical = 20.dp), pressed = {
            dropBanana()

            context.startActivity(Utils.inviteFriends())

        })

    }
}

@Composable
fun ToolbarWithTitle(title: String, backPressed: () -> Unit) {
    Row (
        modifier = Modifier.padding(top = 15.dp),
        verticalAlignment = Alignment.CenterVertically
            ) {
        IconButton(onClick = {
            backPressed()
        }) {
            Icon(
                Icons.Default.ArrowBack,
                tint = IconsDark,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Spacer(
            modifier = Modifier.weight(0.8f)
        )

        Text(
            text = title,
            fontSize = 18.sp,
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            color = IconsDark,
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )
    }

}

@Preview
@Composable
fun InviteFriendsScreen_Preview() {
    InviteFriendsScreenContent() {

    }
}