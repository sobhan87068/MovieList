package com.example.movielist.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movielist.data.model.Movie
import com.example.movielist.ui.designsystem.MovieCard
import com.example.movielist.ui.util.isLandscape

@Composable
fun Home(movies: List<Movie>, scrollState: LazyGridState, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(
            if (isLandscape())
                CELL_COUNT_LANDSCAPE
            else CELL_COUNT_PORTRAIT
        ),
        contentPadding = PaddingValues(horizontal = 5.dp),
        userScrollEnabled = true,
        state = scrollState, modifier = modifier
    ) {
        items(items = movies, key = { item ->
            item.id
        }) {
            MovieCard(movie = it)
        }
    }
}

private const val CELL_COUNT_PORTRAIT = 3
private const val CELL_COUNT_LANDSCAPE = 6

@Preview
@Composable
fun HomePreview() {
    Home(
        movies = listOf(
            Movie(1, "title", ""),
            Movie(2, "title", ""),
            Movie(3, "title", ""),
            Movie(4, "title", ""),
            Movie(5, "title", ""),
        ), scrollState = rememberLazyGridState()
    )
}