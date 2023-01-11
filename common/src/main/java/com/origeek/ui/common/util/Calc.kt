package com.origeek.ui.common.util

/**
 * @program: OrigeekUI
 *
 * @description:
 *
 * @author: JVZIYAOYAO
 *
 * @create: 2022-10-27 18:18
 **/

infix fun Byte.and(mask: Int): Int = toInt() and mask
infix fun Short.and(mask: Int): Int = toInt() and mask
infix fun Int.and(mask: Long): Long = toLong() and mask