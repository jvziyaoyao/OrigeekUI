package com.origeek.ui.sample

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.origeek.ui.common.LazyGridLayout
import com.origeek.ui.common.rememberLazyGridLayoutState
import com.origeek.ui.sample.base.BaseActivity
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBasicContent {
            MainBody()
        }
    }
}

@Composable
fun MainBody() {
    GridLayoutBody()
}

@Composable
fun GridLayoutBody() {
    val columns = 3
    val size = 148
    val scope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridLayoutState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        LazyGridLayout(
            modifier = Modifier.fillMaxSize(),
            columns = columns,
            size = size,
            padding = 2.dp,
            state = lazyGridState,
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)
                    .background(Color.Gray.copy(0.2F))
                    .clickable {
                        scope.launch {
                            lazyGridState.animateScrollToItem(index)
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "$index")
            }
        }
    }
}