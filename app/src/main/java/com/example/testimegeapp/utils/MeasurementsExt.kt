package com.example.testimegeapp.utils

import android.content.res.Resources

fun Int.dp(): Int = (this * density + 0.5f).toInt()

val density: Float
    get() = Resources.getSystem().displayMetrics.density
