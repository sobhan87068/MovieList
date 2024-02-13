package com.example.movielist.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.GlowBg() {
    Box(
        modifier = Modifier
            .offset(y = (-29).dp)
            .padding(end = 10.dp)
            .size(300.dp)
            .background(
                shape = CircleShape,
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x1AFFFFFF),
                        Color(0x00FFFFFF),
                    )
                )
            )
            .align(Alignment.TopEnd)
    )

    Box(
        modifier = Modifier
            .padding(bottom = 100.dp, start = 12.dp)
            .size(300.dp)
            .background(
                shape = CircleShape,
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x0AFFFFFF),
                        Color(0x00FFFFFF),
                    )
                )
            )
            .align(Alignment.BottomStart)
    )
}

@Preview(widthDp = 370, heightDp = 700)
@Composable
fun GlowBgPreview() {
    Box(modifier = Modifier.background(color = Color.Black)) {
        GlowBg()
    }
}