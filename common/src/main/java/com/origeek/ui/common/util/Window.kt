package com.origeek.ui.common.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.Window
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @program: UICommon
 *
 * @description:
 *
 * @author: JVZIYAOYAO
 *
 * @create: 2022-10-17 22:23
 **/

fun hideSystemUI(window: Window) {
    WindowInsetsControllerCompat(window, window.decorView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun showSystemUI(window: Window) {
    WindowInsetsControllerCompat(
        window,
        window.decorView
    ).show(WindowInsetsCompat.Type.systemBars())
}

fun Context.findWindow(): Window? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context.window
        context = context.baseContext
    }
    return null
}