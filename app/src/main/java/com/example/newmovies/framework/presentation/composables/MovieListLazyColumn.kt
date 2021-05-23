package com.example.newmovies.framework.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.newmovies.framework.datasource.cache.model.SavedMovie


@Composable
fun MovieListLazyColumn(
    movie: SavedMovie,
    onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = movie.title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = "Genre: ${movie.genre}"
                    )
                    Text(
                        text = "Year: ${movie.year}"
                    )
                }
                Column() {
                    Text(
                        text = "Director: ${movie.director}"
                    )
                    Text(
                        text = "ImdbRating: ${movie.imdbRating}"
                    )
                }
            }

        }
    }
}
