package com.example.newmovies.framework.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.framework.datasource.network.model.Response

@Composable
fun SearchMovieLazyColumnItem(
    movieSearch: MovieResponse,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = movieSearch.title)
            Text(text = movieSearch.year)
        }
    }
}
