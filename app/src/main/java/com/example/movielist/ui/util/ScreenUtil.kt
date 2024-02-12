package com.example.movielist.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.TypedValueCompat


@Composable
fun dpToPx(dp: Float): Float {
    return TypedValueCompat.dpToPx(dp, LocalContext.current.resources.displayMetrics)
}

@Composable
fun pxToDp(px: Float): Float {
    return TypedValueCompat.pxToDp(px, LocalContext.current.resources.displayMetrics)
}