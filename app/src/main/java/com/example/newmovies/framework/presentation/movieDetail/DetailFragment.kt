package com.example.newmovies.framework.presentation.movieDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
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
                val savedMovie = viewModel.savedMovie.value
                val isCancelDialogOpen = remember { mutableStateOf(false) }
                val loading = viewModel.loading
                val onLoad = viewModel.onLoad.value
                val args: DetailFragmentArgs by navArgs()
                val movieDetailFromNetwork = viewModel.movieDetails.value

                if (!onLoad) {
                    loading.value = true
                    viewModel.onTriggerEvent(MovieEvent.GetSavedMovieEvent(args.imdbId))
                    viewModel.onTriggerEvent(MovieEvent.GetMovieDetailsEvent(args.imdbId))
                }
//                NewMoviesTheme(displayProgressBar = loading) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            elevation = 8.dp,
                            title = {
                                Text(text = args.title)
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    findNavController().navigate(R.id.action_detailFragment_to_movieListFragment)
                                }) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        contentDescription = "return to previous page"
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { isCancelDialogOpen.value = true }
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "delete movie")
                                }
                            }
                        )
                    }
                ) {

                    Column(modifier = Modifier.padding(8.dp)) {
                        CircularIndeterminateProgressBar(isDisplayed = loading.value, 0.3f)

                        if (savedMovie == null && loading.value) {

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
                        } else if (!loading.value && movieDetailFromNetwork == null && onLoad) {

                            Text(text = "Something went wrong")
                        } else if (movieDetailFromNetwork != null) {
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
                        val url = "https://www.imdb.com/title/${args.imdbId}"
                        if (savedMovie != null || movieDetailFromNetwork != null) {
                            Button(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    startActivity(intent)
                                }
                            ) {
                                Text("Check on Imdb Website", Modifier.padding(end = 8.dp))
                                Icon(Icons.Default.ExitToApp, null)
                            }
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
                                    Log.d("getSavedMovieAfter", savedMovie.toString())
//                                    savedMovie.watched = true
//                                    savedMovie.onToWatchList = false

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
                                    Log.d("watch", savedMovie.onToWatchList.toString())

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
                    AlertDialogComponent(
                        isDialogOpen = isCancelDialogOpen,
                        text = "Are you sure you want to remove ${args.title} from your list?",
                        onClick = {
                            viewModel.onTriggerEvent(
                                MovieEvent.DeleteSavedMovieEvent(
                                    args.imdbId
                                )
                            )
                            viewModel.onTriggerEvent(
                                MovieEvent.GetMovieDetailsEvent(args.imdbId)
                            )
                            isCancelDialogOpen.value = false
                            findNavController().navigate(R.id.action_detailFragment_to_movieListFragment)
                        })
                }
            }
        }
    }
}

@Composable
fun AlertDialogComponent(
    isDialogOpen: MutableState<Boolean>,
    text: String,
    onClick: () -> Unit
) {
    if (isDialogOpen.value) {
        AlertDialog(
            onDismissRequest = { isDialogOpen.value = false },
            text = {
                Text(text = text)
            },
            confirmButton = {
                Button(onClick = onClick) {
                    Text(text = "CONFIRM")
                }
            },
            dismissButton = {
                Button(onClick = {
                    isDialogOpen.value = false
                }) {
                    Text(text = "CANCEL")
                }
            }
        )
    }
}