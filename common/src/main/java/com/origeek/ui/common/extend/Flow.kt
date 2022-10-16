package com.origeek.ui.common.extend

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @program: OrigeekUI
 *
 * @description:
 *
 * @author: JVZIYAOYAO
 *
 * @create: 2022-10-16 23:47
 **/

suspend fun <T> SharedFlow<T>.collectOne(scope: CoroutineScope) = suspendCoroutine<T> { c ->
    var job: Job? = null
    fun callback() {
        job?.cancel()
    }
    val flow = this
    job = scope.launch {
        flow.collect {
            c.resume(it)
            callback()
        }
    }
}