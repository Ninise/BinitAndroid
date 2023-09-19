package com.whalescale.binit.view.game

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import com.whalescale.binit.models.GameObject
import com.whalescale.binit.utils.Utils
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun <T> DragTarget(
    modifier: Modifier,
    dataToDrop: T,
    offsetY: Animatable<Float, AnimationVector1D>,
    offsetX: Animatable<Float, AnimationVector1D>,
    objectFell: () -> Unit,
    content: @Composable (() -> Unit)
) {

    val currentState = LocalDragTargetInfo.current
    currentState.ready = false
    currentState.dataToDrop = dataToDrop

    val scope = rememberCoroutineScope()

    Box(modifier = modifier
        .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
        .pointerInput(Unit) {

            forEachGesture {

                awaitPointerEventScope {

                    //Detect a touch down event
                    awaitFirstDown()

                    currentState.ready = false
                    currentState.isDragging = true

                    Utils.log("TEST 0.3 ${(currentState.dataToDrop as? GameObject)?.name}")

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

                                currentState.dragOffset =
                                    Offset(abs(offsetX.value), abs(offsetY.value))

                            }
                        }
                    } while (event.changes.any { it.pressed })

                    currentState.ready = true
                    currentState.dragOffset = Offset.Zero
                    currentState.isDragging = false

                    Utils.log("ONE 1")

                    scope.launch {
                        Utils.log("ONE 2")
                        offsetY.animateTo(
                            targetValue = 1300f,
                            animationSpec = tween(durationMillis = 5_000)) {
                            Utils.log("ONE 3")
                            if (this.value == 700f) {
                                Utils.log("ONE 4")
                                objectFell()
                            }
                        }
                        Utils.log("POST ANIMATION: ${offsetY.value}")
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
    val dragOffset = Offset(dragInfo.dragOffset.x + 100, dragInfo.dragOffset.y + 180)
    val ready = dragInfo.ready

    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier.onGloballyPositioned {
        it.boundsInWindow().let { rect ->

            isCurrentDropTarget = rect.contains(dragPosition + dragOffset)

        }
    }) {
        val data = if (ready && isCurrentDropTarget && !dragInfo.isDragging) {
                dragInfo.dataToDrop as T?
            } else {
                null
            }

        if (ready) {
            Utils.log("TEST 0.4 ${(dragInfo.dataToDrop as? GameObject)?.name}")
        }

        content(isCurrentDropTarget, data)
    }
}

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var dataToDrop by mutableStateOf<Any?>(null)
    var ready by mutableStateOf(false)
}