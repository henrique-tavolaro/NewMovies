package com.example.newmovies.framework.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import com.example.newmovies.framework.datasource.network.model.Search
import com.example.newmovies.framework.presentation.MovieViewModel
import com.example.newmovies.framework.presentation.movieList.MovieListFragmentDirections


@Composable
fun ScreenController(
    navHostController: NavHostController,
    topBarTitle: MutableState<String>,
    navController: NavController,
    movieList: List<MovieResponse?>?,
    viewModel: MovieViewModel,
    toWatchList: List<SavedMovie>,
    watchedList: List<SavedMovie>
) {
    NavHost(
        navController = navHostController, startDestination = "search"
    ) {
        composable("search") {
            topBarTitle.value = "Search For Movies"
            val movieSearch = viewModel.movieList.value
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    TextField(
                        value = viewModel.movieSearch.value,
                        onValueChange = viewModel::onTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Row{
                                Icon(Icons.Default.Search, contentDescription = null)
                                Text(text = "Search movies")
                            }
                        }
                    )
                }
                if (movieList != null) {
                    LazyColumn {
                        items(items = movieSearch) { movie ->
                            SearchMovieLazyColumnItem(
                                movieSearch = movie,
                                onClick = {
                                    val action = MovieListFragmentDirections.actionMovieListFragmentToDetailFragment(
                                            movie.imdbID, movie.title
                                        )
                                    navController.navigate(action)
                                }
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Empty List"
                        )
                    }
                }
            }
        }
        composable("toWatchList") {
            topBarTitle.value = "Movies To Watch"
            LazyColumn() {
                items(items = toWatchList) { movie ->
                    MovieListLazyColumn(
                        movie = movie,
                        onClick = {
                            val action =
                                MovieListFragmentDirections.actionMovieListFragmentToDetailFragment(
                                    movie.imdbID, movie.title
                                )
                            navController.navigate(action)
                        })
                }
            }
        }
        composable("watched") {
            topBarTitle.value = "Watched Movies"
            LazyColumn() {
                items(items = watchedList) { movie ->
                    MovieListLazyColumn(
                        movie = movie,
                        onClick = {
                            val action =
                                MovieListFragmentDirections.actionMovieListFragmentToDetailFragment(
                                    movie.imdbID, movie.title
                                )
                            navController.navigate(action)
                        })
                }
            }
        }
    }
}