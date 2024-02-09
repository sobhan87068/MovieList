package com.example.movielist.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movielist.R


@Composable
fun Error() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(
                    shape = CircleShape,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xff092040),
                            Color(0xff031329)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.sad_face), contentDescription = "error")
        }
        Text(
            text = "Connection glitch", color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Seems like there's an internet connection problem.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(corner = CornerSize(24.dp))
                )
                .padding(horizontal = 32.dp, vertical = 12.dp)
        ) {
            Text(text = "Retry", color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview
@Composable
fun ErrorPreview() {
    Error()
}