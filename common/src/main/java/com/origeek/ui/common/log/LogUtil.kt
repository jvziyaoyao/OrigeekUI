package com.origeek.ui.common.log

import android.util.Log

/**
 * @program: UICommon
 *
 * @description:
 *
 * @author: JVZIYAOYAO
 *
 * @create: 2022-10-06 00:22
 **/
const val TAG = "LogUtil"

suspend fun testTimeSuspend(label: String = "nothing", action: suspend () -> Unit) {
    val t0 = System.currentTimeMillis()
    action()
    val t1 = System.currentTimeMillis()
    Log.i(TAG, "testTimeSuspend -> label is $label: ${t1 - t0}")
}

fun testTime(label: String = "nothing", action: () -> Unit) {
    val t0 = System.currentTimeMillis()
    action()
    val t1 = System.currentTimeMillis()
    Log.i(TAG, "testTime -> label is $label: ${t1 - t0}")
}