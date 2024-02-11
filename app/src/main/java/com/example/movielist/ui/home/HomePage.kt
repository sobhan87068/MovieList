package com.example.movielist.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.R
import com.example.movielist.data.model.Movie

@Composable
fun Home(movies: List<Movie>, scrollState: LazyGridState, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(CELL_COUNT),
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

private const val CELL_COUNT = 3

@Composable
fun MovieCard(movie: Movie) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth(.3f)
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 20.dp)
            .clickable {
                Toast
                    .makeText(context, movie.title, Toast.LENGTH_LONG)
                    .show()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample_poster),
            contentDescription = "thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .background(shape = RoundedCornerShape(10.dp), color = Color.Transparent)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = movie.title, fontWeight = FontWeight(600),
            fontSize = 12.sp, lineHeight = 14.sp,
            maxLines = 1, overflow = TextOverflow.Ellipsis
        )
    }
}

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