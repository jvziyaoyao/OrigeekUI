package com.origeek.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.ceil

/**
 * @program: UICommon
 *
 * @description:
 *
 * @author: JVZIYAOYAO
 *
 * @create: 2022-10-06 00:18
 **/

@Composable
fun GridLayout(
    modifier: Modifier = Modifier,
    columns: Int,
    size: Int,
    padding: Dp = 0.dp,
    block: @Composable (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val line = ceil(size.toDouble() / columns).toInt()
        val halfPadding = padding.div(2)
        for (c in 0 until line) {
            if (c != 0) Spacer(modifier = Modifier.height(halfPadding))
            Row(modifier = Modifier.fillMaxWidth()) {
                for (r in 0 until columns) {
                    val index = c * columns + r
                    if (r != 0) Spacer(modifier = Modifier.width(halfPadding))
                    Box(
                        modifier = Modifier
                            .weight(1F)
                    ) {
                        if (index < size) {
                            block(index)
                        }
                    }
                    if (r != columns - 1) Spacer(modifier = Modifier.width(halfPadding))
                }
            }
            if (c != line - 1) Spacer(modifier = Modifier.height(halfPadding))
        }
    }
}

@Composable
fun LazyGridLayout(
    modifier: Modifier = Modifier,
    columns: Int,
    size: Int,
    padding: Dp = 0.dp,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(),
    block: @Composable (Int) -> Unit,
) {
    val line = ceil(size.toDouble() / columns).toInt()
    val halfPadding = padding.div(2)
    LazyColumn(
        modifier = modifier,
        state = state,
        content = {
            items(count = line, key = { it }) { c ->
                if (c != 0) Spacer(modifier = Modifier.height(halfPadding))
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (r in 0 until columns) {
                        val index = c * columns + r
                        if (r != 0) Spacer(modifier = Modifier.width(halfPadding))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if (index < size) {
                                block(index)
                            }
                        }
                        if (r != columns - 1) Spacer(modifier = Modifier.width(halfPadding))
                    }
                }
                if (c != line - 1) Spacer(modifier = Modifier.height(halfPadding))
            }
        }, contentPadding = contentPadding
    )
}

@Composable
fun ScaleGrid(
    onTap: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val itemScale = remember { Animatable(1F) }
    Box(
        modifier = Modifier
            .fillMaxSize(itemScale.value)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        // 这里开始
                        scope.launch {
                            itemScale.animateTo(0.84F)
                        }
                        var move = false
                        do {
                            val event = awaitPointerEvent()
                            if (!move) {
                                move = event.type == PointerEventType.Move
                                break
                            }
                        } while (event.changes.any { it.pressed })
                        // 这里结束
                        scope.launch {
                            itemScale.animateTo(1F)
                        }
                        if (move) {
                            return@awaitPointerEventScope
                        }
                        onTap()
                    }
                }
            }
    ) {
        content()
    }
}