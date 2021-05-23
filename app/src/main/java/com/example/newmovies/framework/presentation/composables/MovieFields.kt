package com.example.newmovies.framework.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.newmovies.R
import com.example.newmovies.util.loadImageUri

@Composable
fun MovieFields(
    year: String,
    director: String,
    genre: String,
    imdbRating: String,
    poster: String,
    plot: String,
    actors: String
){
    val state = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(state)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = "Year: $year"
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = "Director: $director"
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = "Genre: $genre"
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = "ImdbRating: $imdbRating"
                )
            }
            val image = loadImageUri(
                url = poster,
                defaultImage = R.drawable.placeholder
            ).value
            image?.let { image ->
                Image(
                    bitmap = image.asImageBitmap(),
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    contentDescription = "Movie Poster"
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            text = "Cast: $actors"
        )
        Text(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            text = "Plot: $plot"
        )

    }
}