package com.whalescale.binit.view.custom_views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whalescale.binit.ui.theme.Inter
import com.whalescale.binit.ui.theme.MainOrange

@Composable
fun DefaultButton(text: String, modifier: Modifier = Modifier, icon: Int? = null, pressed: () -> Unit) {

    Button(
        onClick = {
            pressed()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MainOrange),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)) {

        Text(
            text = text,
            color = Color.White,
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(
                horizontal = 5.dp
            )
        )

        icon?.let {
            Icon(
                painterResource(id = it),
                contentDescription = "",
                modifier = Modifier
                    .size(16.dp)
            )
        }

    }
}