package com.example.movielist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movielist.base.ViewState
import com.example.movielist.ui.home.Error
import com.example.movielist.ui.home.Home
import com.example.movielist.ui.home.HomeAction
import com.example.movielist.ui.home.HomeState
import com.example.movielist.ui.home.HomeViewModel
import com.example.movielist.ui.home.Loading
import com.example.movielist.ui.theme.Grey10
import com.example.movielist.ui.theme.Grey40
import com.example.movielist.ui.theme.MovieListTheme
import com.example.movielist.ui.theme.Red
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState: HomeState by mutableStateOf(HomeState.Idle)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.onEach {
                    uiState = it
                }.collect()
            }
        }

        viewModel.submitAction(HomeAction.LoadMore)

        setContent {
            MovieListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Switcher(uiState) {
                        viewModel.submitAction(HomeAction.LoadMore)
                    }
                }
            }
        }
    }
}

@Composable
fun Switcher(state: ViewState, onEndReached: () -> Unit) {
    val scrollState = rememberLazyGridState()
    val endReached by remember {
        derivedStateOf {
            !scrollState.canScrollForward
        }
    }

    LaunchedEffect(key1 = endReached) {
        if (endReached)
            onEndReached()
    }

    when (state) {
        HomeState.Idle -> Text(text = "start")
        HomeState.Loading -> Loading()
        is HomeState.Error -> Error(onEndReached)
        is HomeState.NewPage -> {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = "Discover",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(vertical = 20.dp),
                            color = MaterialTheme.colorScheme.tertiary,
                            lineHeight = 29.sp,
                            fontSize = 24.sp,
                            fontWeight = FontWeight(600)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.bazaar_logo),
                            contentDescription = "logo",
                            modifier = Modifier
                                .align(alignment = Alignment.CenterEnd)
                                .padding(end = 20.dp)
                                .size(36.dp)
                        )
                    }
                    Home(
                        movies = state.data, scrollState = scrollState,
                        modifier = Modifier.weight(1f, fill = true)
                    )

                    if (state is HomeState.NewPage.PaginationLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 20.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    } else if (state is HomeState.NewPage.PaginationError) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 36.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.message ?: "Something went wrong",
                                modifier = Modifier.weight(1f)
                            )

                            OutlinedButton(
                                onClick = onEndReached,
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
                }

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
        }
    }
}

@Preview
@Composable
fun SwitcherPreview() {
    Switcher(state = HomeState.NewPage.PaginationError(listOf())) {}
}