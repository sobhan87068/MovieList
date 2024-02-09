package com.example.movielist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

        var uiState: HomeState by mutableStateOf(HomeState.Loading)

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
fun Switcher(state: ViewState) {
    when (state) {
        HomeState.Idle -> Text(text = "start")
        HomeState.Loading -> Loading()
        is HomeState.Error -> Error()
        is HomeState.Success -> {
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
                Home(movies = state.data)
            }
        }
    }
}

@Preview
@Composable
fun SwitcherPreview() {
    Switcher(state = HomeState.Loading)
}