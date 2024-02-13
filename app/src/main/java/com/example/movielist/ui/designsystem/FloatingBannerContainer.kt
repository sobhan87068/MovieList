package com.example.movielist.ui.designsystem

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.R
import com.example.movielist.ui.util.dpToPx
import com.example.movielist.ui.util.pxToDp


@Composable
fun FloatingBannerContainer(
    isFinished: Boolean,
    content: @Composable BoxScope.() -> Unit = {}
) {
    val context = LocalContext.current
    val animationDuration = 1000
    val boxHeight = context.resources.displayMetrics.heightPixels
    val boxWidth = context.resources.displayMetrics.widthPixels

    val transition = updateTransition(targetState = isFinished, "logo animation")

    val logoSize = transition.animateDp(
        label = "logo size animation",
        transitionSpec = { tween(durationMillis = animationDuration) }
    ) {
        if (it) 36.dp else 80.dp
    }.value

    val logoOffset = transition.animateIntOffset(
        label = "logo offset animation",
        transitionSpec = { tween(durationMillis = animationDuration) }
    ) {
        if (it) IntOffset(
            x = boxWidth - dpToPx(dp = logoSize.value.toInt() + 20),
            y = dpToPx(dp = 10)
        )
        else IntOffset(
            x = (boxWidth - dpToPx(dp = logoSize.value.toInt())) / 2,
            y = boxHeight / 2 - dpToPx(dp = logoSize.value.toInt())
        )
    }.value

    val contentAlpha = transition.animateFloat(
        label = "content alpha animation",
        transitionSpec = { tween(durationMillis = animationDuration) }
    ) {
        if (it) 1f else 0f
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bazaar_logo),
            contentDescription = "logo",
            modifier = Modifier
                .size(logoSize)
                .offset {
                    logoOffset
                }
        )
        if (isFinished) {
            Text(
                text = "Discover",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                color = MaterialTheme.colorScheme.tertiary,
                lineHeight = 29.sp,
                fontSize = 24.sp,
                fontWeight = FontWeight(600)
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = logoSize / 2 + 50.dp)
                    .size(32.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = logoSize + pxToDp(px = logoOffset.y).dp + 52.dp
                )
                .alpha(contentAlpha.value)
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    var isFinished by remember {
        mutableStateOf(false)
    }

    Button(onClick = { isFinished = true }) {
        Text(text = "Finish")
    }
    FloatingBannerContainer(isFinished)
}