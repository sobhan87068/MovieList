package com.example.movielist.ui.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.TypedValueCompat


@Composable
fun dpToPx(dp: Int): Int {
    return TypedValueCompat.dpToPx(
        dp.toFloat(),
        LocalContext.current.resources.displayMetrics
    ).toInt()
}

@Composable
fun pxToDp(px: Int): Int {
    return TypedValueCompat.pxToDp(
        px.toFloat(),
        LocalContext.current.resources.displayMetrics
    ).toInt()
}

@Composable
fun isLandscape(): Boolean {
    val config = LocalConfiguration.current
    return config.orientation == Configuration.ORIENTATION_LANDSCAPE
}