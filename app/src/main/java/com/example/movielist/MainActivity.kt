package com.example.movielist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movielist.ui.designsystem.FloatingBannerContainer
import com.example.movielist.ui.designsystem.GlowBg
import com.example.movielist.ui.designsystem.LoadMoreError
import com.example.movielist.ui.home.Error
import com.example.movielist.ui.home.Home
import com.example.movielist.ui.home.HomeAction
import com.example.movielist.ui.home.HomeState
import com.example.movielist.ui.home.HomeViewModel
import com.example.movielist.ui.theme.MovieListTheme
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
fun Switcher(state: HomeState, loadMore: () -> Unit) {
    val scrollState = rememberLazyGridState()
    val endReached by remember {
        derivedStateOf {
            !scrollState.canScrollForward
        }
    }

    LaunchedEffect(key1 = endReached) {
        if (endReached)
            loadMore()
    }

    if (state == HomeState.Idle) {
        Text(text = "start")
    } else if (state is HomeState.Error && state.data.isEmpty()) {
        Error(loadMore)
    } else {
        FloatingBannerContainer(
            state !is HomeState.Loading || state.data.isNotEmpty(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
            ) {
                Home(
                    movies = state.data, scrollState = scrollState,
                    modifier = Modifier.weight(1f, fill = true)
                )

                if (state is HomeState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 20.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                } else if (state is HomeState.Error) {
                    LoadMoreError(message = state.message, loadMore)
                }
            }

            GlowBg()
        }
    }
}

@Preview
@Composable
fun SwitcherPreview() {
    Switcher(state = HomeState.Error(listOf())) {}
}