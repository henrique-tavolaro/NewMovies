package com.example.newmovies.framework.presentation.movieDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newmovies.R
import com.example.newmovies.framework.presentation.MovieViewModel
import com.example.newmovies.framework.presentation.composables.CircularIndeterminateProgressBar
import com.example.newmovies.framework.presentation.composables.MovieFields
import com.example.newmovies.framework.presentation.state.MovieEvent
import com.example.newmovies.framework.presentation.ui.theme.NewMoviesTheme
import com.example.newmovies.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()


    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading
                val onLoad = viewModel.onLoad.value
                val args: DetailFragmentArgs by navArgs()
                val movieDetailFromNetwork = viewModel.movieDetails.value
                val savedMovie = viewModel.savedMovie.value
                if(!onLoad){
                    loading.value = true
                    viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
                    viewModel.onTriggerEvent(MovieEvent.GetMovieDetailsEvent(args.imdbId))
                }
//                NewMoviesTheme(displayProgressBar = loading) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = args.title)
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        findNavController().navigate(R.id.action_detailFragment_to_movieListFragment)
                                    }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                                    }
                                }
                            )
                        }
                    ) {

                        Column(modifier = Modifier.padding(8.dp)) {
                            CircularIndeterminateProgressBar(isDisplayed = loading.value, 0.3f)

                            if(savedMovie == null && loading.value){

                            } else if (savedMovie != null) {
                                MovieFields(
                                    savedMovie.year,
                                    savedMovie.director,
                                    savedMovie.genre,
                                    savedMovie.imdbRating,
                                    savedMovie.poster,
                                    savedMovie.plot,
                                    savedMovie.actors
                                )
                            }else if(!loading.value && movieDetailFromNetwork == null && onLoad) {

                                Text(text = "Something went wrong")
                            }
                            else if (movieDetailFromNetwork != null) {
                                MovieFields(
                                    movieDetailFromNetwork.year,
                                    movieDetailFromNetwork.director,
                                    movieDetailFromNetwork.genre,
                                    movieDetailFromNetwork.imdbRating,
                                    movieDetailFromNetwork.poster,
                                    movieDetailFromNetwork.plot,
                                    movieDetailFromNetwork.actors
                                )
                            }
                            if (savedMovie != null && savedMovie.onToWatchList) {
                                Button(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    onClick = {

                                        viewModel.onTriggerEvent(
                                            MovieEvent.UpdateMovieAsWatchedEvent(
                                                args.imdbId
                                            )
                                        )
                                        savedMovie.watched = true
                                        savedMovie.onToWatchList = false

                                        viewModel.onTriggerEvent(
                                            MovieEvent.GetSavedMovieEvent(
                                                savedMovie.imdbID
                                            )
                                        )

                                    }
                                ) {
                                    Icon(Icons.Default.Movie, contentDescription = "Watched")
                                    Text(
                                        modifier = Modifier.padding(start = 4.dp),
                                        text = "Rank and add to watched"
                                    )
                                }
                            } else if (savedMovie != null && savedMovie.watched) {
                                Button(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    onClick = {

                                        viewModel.onTriggerEvent(
                                            MovieEvent.UpdateMovieToWatchListEvent(
                                                args.imdbId
                                            )
                                        )


                                        savedMovie.onToWatchList = true
                                        viewModel.onTriggerEvent(
                                            MovieEvent.GetSavedMovieEvent(
                                                savedMovie.imdbID
                                            )
                                        )

                                    }
                                ) {
                                    Icon(
                                        Icons.Default.WatchLater,
                                        contentDescription = "Watch Later"
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 4.dp),
                                        text = "Add to watch list"
                                    )
                                }
                            } else if (movieDetailFromNetwork != null) {
                                Row {
                                    Button(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .weight(1f),
                                        onClick = {
                                            viewModel.onTriggerEvent(
                                                MovieEvent.AddMovieToWatchListEvent(
                                                    args.imdbId
                                                )
                                            )
                                            viewModel.onTriggerEvent(
                                                MovieEvent.GetSavedMovieEvent(
                                                    args.imdbId
                                                )
                                            )
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.WatchLater,
                                            contentDescription = "Watch Later"
                                        )
                                        Text(
                                            modifier = Modifier.padding(start = 4.dp),
                                            text = "Add to watch list"
                                        )
                                    }
                                    Button(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .weight(1f),
                                        onClick = {
                                            viewModel.onTriggerEvent(
                                                MovieEvent.AddMovieAsWatchedEvent(
                                                    args.imdbId
                                                )
                                            )
                                            viewModel.onTriggerEvent(
                                                MovieEvent.GetSavedMovieEvent(
                                                    args.imdbId
                                                )
                                            )
                                        }
                                    ) {
                                        Icon(Icons.Default.Movie, contentDescription = "Watched")
                                        Text(
                                            modifier = Modifier.padding(start = 4.dp),
                                            text = "Rank and add to watched"
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }
    }
//}

//}
//
//                //movie already on room and onToWatchList and updated to the watchedList
//                if (savedMovie != null && savedMovie.onToWatchList) {
//                    Column(modifier = Modifier.padding(8.dp)) {
//                        MovieFields(
//                            savedMovie.year,
//                            savedMovie.director,
//                            savedMovie.genre,
//                            savedMovie.imdbRating,
//                            savedMovie.poster,
//                            savedMovie.plot,
//                            savedMovie.actors
//                        )
//                        Button(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .fillMaxWidth(),
//                            onClick = {
//                                viewModel.onTriggerEvent(MovieEvent.UpdateMovieAsWatchedEvent(args.imdbId))
//                                viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//
//
//                            }
//                        ) {
//                            Icon(Icons.Default.Movie, contentDescription = "Watched")
//                            Text(
//                                modifier = Modifier.padding(start = 4.dp),
//                                text = "Rank and add to watched"
//                            )
//                        }
//                    }
//                } //movie already watched and assigned toWatchList again
//                else if (savedMovie != null && savedMovie.watched) {
//                    Column(modifier = Modifier.padding(8.dp)) {
//                        MovieFields(
//                            savedMovie.year,
//                            savedMovie.director,
//                            savedMovie.genre,
//                            savedMovie.imdbRating,
//                            savedMovie.poster,
//                            savedMovie.plot,
//                            savedMovie.actors
//                        )
//                        Button(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .fillMaxWidth(),
//                            onClick = {
//                                viewModel.onTriggerEvent(MovieEvent.UpdateMovieToWatchListEvent(args.imdbId))
//                                viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//                            }
//                        ) {
//                            Icon(Icons.Default.WatchLater, contentDescription = "Watch Later")
//                            Text(
//                                modifier = Modifier.padding(start = 4.dp),
//                                text = "Add to watch list"
//                            )
//                        }
//                    }
//                }
//                //movie not saved yet
//                else if (movieDetailFromNetwork != null) {
//                    Column(modifier = Modifier.padding(8.dp)) {
//                        MovieFields(
//                            movieDetailFromNetwork.year,
//                            movieDetailFromNetwork.director,
//                            movieDetailFromNetwork.genre,
//                            movieDetailFromNetwork.imdbRating,
//                            movieDetailFromNetwork.poster,
//                            movieDetailFromNetwork.plot,
//                            movieDetailFromNetwork.actors
//                        )
//                        Row(
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Button(
//                                modifier = Modifier
//                                    .padding(8.dp)
//                                    .weight(1f),
//                                onClick = {
//                                    viewModel.onTriggerEvent(
//                                        MovieEvent.AddMovieToWatchListEvent(
//                                            args.imdbId
//                                        )
//                                    )
//                                    viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//
//                                }
//                            ) {
//                                Icon(
//                                    Icons.Default.WatchLater,
//                                    contentDescription = "Watch Later"
//                                )
//                                Text(
//                                    modifier = Modifier.padding(start = 4.dp),
//                                    text = "Add to watch list"
//                                )
//                            }
//                            Button(
//                                modifier = Modifier
//                                    .padding(8.dp)
//                                    .weight(1f),
//                                onClick = {
//                                    viewModel.onTriggerEvent(MovieEvent.AddMovieAsWatchedEvent(args.imdbId))
//                                    viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//
//
//                                }
//                            ) {
//                                Icon(Icons.Default.Movie, contentDescription = "Watched")
//                                Text(
//                                    modifier = Modifier.padding(start = 4.dp),
//                                    text = "Rank and add to watched"
//                                )
//                            }
//                        }
//                    }
//                } else {
//                    Text(text = "Something went wrong")
//                }
//            }

//
//
//val args: DetailFragmentArgs by navArgs()
//val movieDetailFromNetwork = viewModel.movieDetails.value
//val savedMovie = viewModel.savedMovie.value
//viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//viewModel.onTriggerEvent(MovieEvent.GetMovieDetailsEvent(args.imdbId))
//
//Scaffold(
//topBar = {
//    TopAppBar(
//        title = {
//            Text(text = args.title)
//        },
//        navigationIcon = {
//            IconButton(onClick = {
//                findNavController().navigate(R.id.action_detailFragment_to_movieListFragment)
//            }) {
//                Icon(Icons.Default.ArrowBack, contentDescription = null)
//            }
//        }
//    )
//}
//) {
//    //movie already on room and onToWatchList and updated to the watchedList
//    if (savedMovie != null && savedMovie.onToWatchList) {
//        Column(modifier = Modifier.padding(8.dp)) {
//            MovieFields(
//                savedMovie.year,
//                savedMovie.director,
//                savedMovie.genre,
//                savedMovie.imdbRating,
//                savedMovie.poster,
//                savedMovie.plot,
//                savedMovie.actors
//            )
//            Button(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxWidth(),
//                onClick = {
//                    viewModel.onTriggerEvent(MovieEvent.UpdateMovieAsWatchedEvent(args.imdbId))
//                    viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//
//
//                }
//            ) {
//                Icon(Icons.Default.Movie, contentDescription = "Watched")
//                Text(
//                    modifier = Modifier.padding(start = 4.dp),
//                    text = "Rank and add to watched"
//                )
//            }
//        }
//    } //movie already watched and assigned toWatchList again
//    else if (savedMovie != null && savedMovie.watched) {
//        Column(modifier = Modifier.padding(8.dp)) {
//            MovieFields(
//                savedMovie.year,
//                savedMovie.director,
//                savedMovie.genre,
//                savedMovie.imdbRating,
//                savedMovie.poster,
//                savedMovie.plot,
//                savedMovie.actors
//            )
//            Button(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxWidth(),
//                onClick = {
//                    viewModel.onTriggerEvent(MovieEvent.UpdateMovieToWatchListEvent(args.imdbId))
//                    viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//                }
//            ) {
//                Icon(Icons.Default.WatchLater, contentDescription = "Watch Later")
//                Text(
//                    modifier = Modifier.padding(start = 4.dp),
//                    text = "Add to watch list"
//                )
//            }
//        }
//    }
//    //movie not saved yet
//    else if (movieDetailFromNetwork != null) {
//        Column(modifier = Modifier.padding(8.dp)) {
//            MovieFields(
//                movieDetailFromNetwork.year,
//                movieDetailFromNetwork.director,
//                movieDetailFromNetwork.genre,
//                movieDetailFromNetwork.imdbRating,
//                movieDetailFromNetwork.poster,
//                movieDetailFromNetwork.plot,
//                movieDetailFromNetwork.actors
//            )
//            Row(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Button(
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .weight(1f),
//                    onClick = {
//                        viewModel.onTriggerEvent(MovieEvent.AddMovieToWatchListEvent(args.imdbId))
//                        viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//
//                    }
//                ) {
//                    Icon(
//                        Icons.Default.WatchLater,
//                        contentDescription = "Watch Later"
//                    )
//                    Text(
//                        modifier = Modifier.padding(start = 4.dp),
//                        text = "Add to watch list"
//                    )
//                }
//                Button(
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .weight(1f),
//                    onClick = {
//                        viewModel.onTriggerEvent(MovieEvent.AddMovieAsWatchedEvent(args.imdbId))
//                        viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
//
//
//                    }
//                ) {
//                    Icon(Icons.Default.Movie, contentDescription = "Watched")
//                    Text(
//                        modifier = Modifier.padding(start = 4.dp),
//                        text = "Rank and add to watched"
//                    )
//                }
//            }
//        }
//    } else {
//        Text(text = "Something went wrong")
//    }
//}
//}
//}
//}
//}
//
