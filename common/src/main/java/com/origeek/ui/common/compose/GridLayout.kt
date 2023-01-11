package com.origeek.ui.common.compose

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

class LazyGridLayoutState {

    lateinit var lazyListState: LazyListState

    var columns = 0
        internal set

    private fun getScrollIndex(index: Int): Int {
        return index.div(columns)
    }

    suspend fun scrollToItem(
        index: Int,
        scrollOffset: Int = 0
    ) {
        lazyListState.scrollToItem(getScrollIndex(index), scrollOffset)
    }

    suspend fun animateScrollToItem(
        index: Int,
        scrollOffset: Int = 0
    ) {
        lazyListState.animateScrollToItem(getScrollIndex(index), scrollOffset)
    }

}

@Composable
fun rememberLazyGridLayoutState(): LazyGridLayoutState {
    val lazyListState = rememberLazyListState()
    val lazyGridState = remember {
        LazyGridLayoutState()
    }
    lazyGridState.lazyListState = lazyListState
    return lazyGridState
}

@Composable
fun LazyGridLayout(
    modifier: Modifier = Modifier,
    columns: Int,
    size: Int,
    padding: Dp = 0.dp,
    state: LazyGridLayoutState = rememberLazyGridLayoutState(),
    contentPadding: PaddingValues = PaddingValues(),
    block: @Composable (Int) -> Unit,
) {
    val line = ceil(size.toDouble() / columns).toInt()
    val halfPadding = padding.div(2)
    state.columns = columns
    LazyColumn(
        modifier = modifier,
        state = state.lazyListState,
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

const val DEFAULT_GRID_SCALE_SIZE = 0.84F

@Composable
fun ScaleGrid(
    modifier: Modifier = Modifier,
    scaleSize: Float = DEFAULT_GRID_SCALE_SIZE,
    onTap: () -> Unit = {},
    content: @Composable (Float) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val itemScale = remember { Animatable(1F) }
    Box(
        modifier = modifier
            .fillMaxSize(itemScale.value)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        // 这里开始
                        scope.launch {
                            itemScale.animateTo(scaleSize)
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
        content(itemScale.value)
    }
}