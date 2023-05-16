package com.ndteam.wasteandroidapp.view.game

import androidx.compose.animation.core.*
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.utils.Utils
import kotlinx.coroutines.launch
import java.util.Random
import kotlin.math.abs
import kotlin.math.roundToInt

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun <T> DragTarget(
    modifier: Modifier,
    dataToDrop: T,
    content: @Composable (() -> Unit)
) {

    val currentState = LocalDragTargetInfo.current

    val offsetY = remember { Animatable(0f) }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit, block = {
        scope.launch {
            offsetY.animateTo(
                targetValue = 1500f,
                animationSpec = tween(durationMillis = 5_000)
            )
        }

        scope.launch {

            offsetX.animateTo(
                targetValue = 500f,
                animationSpec = tween(durationMillis = 100)
            )
        }
    })

    Box(modifier = modifier.offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
        .pointerInput(Unit) {

            forEachGesture {

                awaitPointerEventScope {

                    //Detect a touch down event
                    awaitFirstDown()

                    currentState.dataToDrop = dataToDrop
                    currentState.isDragging = true

                    do {
                        val event: PointerEvent = awaitPointerEvent()
                        event.changes.forEach { pointerInputChange: PointerInputChange ->
                            //Consume the change
                            scope.launch {

                                offsetY.snapTo(
                                    offsetY.value + pointerInputChange.positionChange().y
                                )
                                offsetX.snapTo(
                                    offsetX.value + pointerInputChange.positionChange().x
                                )

                                currentState.dragOffset = Offset(abs(offsetX.value), abs(offsetY.value))

                            }
                        }
                    } while (event.changes.any { it.pressed })

                    currentState.dragOffset = Offset.Zero
                    currentState.isDragging = false

                    scope.launch {
                        offsetY.animateTo(
                            targetValue = 1500f,
                            animationSpec = tween(durationMillis = 5_000)
                            )
                    }

                }
            }
        }) {

        content()

    }
}


@Composable
fun <T> DropTarget(
    modifier: Modifier,
    content: @Composable() (BoxScope.(isInBound: Boolean, data: T?) -> Unit)
) {

    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset

    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier.onGloballyPositioned {
        it.boundsInWindow().let { rect ->

            isCurrentDropTarget = rect.contains(dragPosition + dragOffset)

        }
    }) {
        val data = if (isCurrentDropTarget && !dragInfo.isDragging) {
                dragInfo.dataToDrop as T?
            } else {
                null
            }

        content(isCurrentDropTarget, data)
    }
}

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var dataToDrop by mutableStateOf<Any?>(null)
}