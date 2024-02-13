package com.example.movielist.ui.designsystem

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.ui.theme.Grey10
import com.example.movielist.ui.theme.Grey40
import com.example.movielist.ui.theme.MovieListTheme
import com.example.movielist.ui.theme.Red

@Composable
fun LoadMoreError(message: String? = null, onRetry: () -> Unit = {}) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message ?: "Something went wrong",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight(600),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )

        OutlinedButton(
            onClick = onRetry,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Grey10
            ), shape = RoundedCornerShape(4.dp),
            border = BorderStroke(width = 1.dp, color = Grey40)
        ) {
            Text(
                text = "Try Again", color = Red, lineHeight = 20.sp,
                fontWeight = FontWeight(400), fontSize = 14.sp
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF131313
)
@Composable
fun ErrorPreviewDark() {
    MovieListTheme {
        LoadMoreError()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun ErrorPreviewLight() {
    MovieListTheme {
        LoadMoreError()
    }
}