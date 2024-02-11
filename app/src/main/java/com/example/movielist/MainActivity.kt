package com.example.movielist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        is HomeState.Error -> Error()
        is HomeState.NewPage -> {
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Discover", modifier = Modifier.align(Alignment.Center))
                    Image(
                        painter = painterResource(id = R.drawable.bazaar_logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .size(36.dp)
                    )
                }
                Home(
                    movies = state.data, scrollState = scrollState,
                    modifier = Modifier.weight(1f, fill = true)
                )

                if (state is HomeState.NewPage.PaginationLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else if (state is HomeState.NewPage.PaginationError) {
                    Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = state.message ?: "Something went wrong",
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedButton(
                            onClick = {},
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xff191A1F)
                            ), shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(width = 1.dp, color = Color(0xff44464E))
                        ) {
                            Text(text = "Try Again")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SwitcherPreview() {
    Switcher(state = HomeState.Loading) {}
}