package com.example.newmovies.framework.presentation.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.newmovies.R
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import com.example.newmovies.util.loadImageUri


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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val imagea = movie.poster
                val image = loadImageUri(
                    url = imagea,
                    defaultImage = R.drawable.placeholder
                ).value
//                image?.let { image ->
//                    Image(
//                        bitmap = image.asImageBitmap(),
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .weight(1f),
//                        contentDescription = "Movie Poster"
//                    )
//                }

                Column() {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = movie.title,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "Genre: ${movie.genre}"
                    )
                    Text(
                        text = "Year: ${movie.year}"
                    )
                }
            }
        }
    }
}
