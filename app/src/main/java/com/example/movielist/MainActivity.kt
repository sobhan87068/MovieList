package com.example.movielist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movielist.base.ViewState
import com.example.movielist.ui.home.HomeAction
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

        var uiState: ViewState by mutableStateOf(ViewState.Idle)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.onEach { uiState = it }
                    .collect()
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
                    Switcher(uiState)
                }
            }
        }
    }
}

@Composable
fun Switcher(state: ViewState, modifier: Modifier = Modifier) {
    when (state) {
        ViewState.Idle -> Text(text = "start")
        ViewState.Loading -> Text(text = "loading")
        is ViewState.Error -> Text(text = "Error")
        is ViewState.Success<*> -> Text(text = "got ${(state.data as? List<*>)?.size} items")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieListTheme {
        Switcher(ViewState.Loading)
    }
}