package com.example.testimegeapp.utils

import android.view.View
import androidx.core.view.ViewCompat

fun View.setLightNavigationBars(light: Boolean) =
    ViewCompat.getWindowInsetsController(this)?.let { controller ->
        if (controller.isAppearanceLightStatusBars != light) {
            controller.isAppearanceLightNavigationBars = light
            controller.isAppearanceLightStatusBars = light
        }
    }
