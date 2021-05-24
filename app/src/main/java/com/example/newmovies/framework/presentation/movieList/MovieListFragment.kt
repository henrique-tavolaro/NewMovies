package com.example.newmovies.framework.presentation.movieList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.newmovies.R
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import com.example.newmovies.framework.presentation.MovieViewModel
import com.example.newmovies.framework.presentation.bottomNavigation.Screen
import com.example.newmovies.framework.presentation.composables.ScreenController
import com.example.newmovies.framework.presentation.state.MovieEvent
import com.example.newmovies.framework.presentation.ui.theme.NewMoviesTheme
import com.example.newmovies.util.ConnectivityManager
import com.example.newmovies.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()


    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
//                val loading = viewModel.loading.value
                NewMoviesTheme(isNetworkAvailable = connectivityManager.isNetworkAvailable.value) {
                    val navController = rememberNavController()
                    val title = remember { mutableStateOf("Search Movie") }
                    val watchedList = remember { mutableStateOf(mutableListOf<SavedMovie>()) }
                    val toWatchList = remember { mutableStateOf(mutableListOf<SavedMovie>()) }
                    val movieListFromDB = viewModel.getAllSaved.value
                    viewModel.onTriggerEvent(MovieEvent.GetAllSavedMovieEvent)
                    Log.d("TAG", movieListFromDB.toString())
                    val searchMovieList = remember { mutableStateOf(viewModel.movieList.value) }
                    for (movie in movieListFromDB) {
                        if (movie.watched) {
                            watchedList.value.add(movie)
                        }
                        if (movie.onToWatchList) {
                            toWatchList.value.add(movie)
                        }
                    }
                    Log.d("watched", watchedList.value.toString())
                    Log.d("watch", toWatchList.value.toString())

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = title.value)
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        viewModel.onTriggerEvent(MovieEvent.GetAllSavedMovieEvent)
                                    }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            val items = listOf(
                                Screen.Search,
                                Screen.ToWatchList,
                                Screen.Watched
                            )
                            BottomNavigation {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

                                items.forEach {
                                    BottomNavigationItem(
                                        icon = { Icon(it.icon, contentDescription = null) },
                                        label = { it.label },
                                        selected = currentRoute == it.route,
                                        onClick = {
                                            navController.popBackStack(
                                                navController.graph.startDestination, false
                                            )
                                            if (currentRoute != it.route) {
                                                navController.navigate(it.route)
                                            }
                                        })
                                }
                            }
                        }
                    ) {
                        ScreenController(
                            navHostController = navController,
                            topBarTitle = title,
                            navController = findNavController(),
                            movieList = searchMovieList.value,
                            viewModel = viewModel,
                            toWatchList = toWatchList.value,
                            watchedList = watchedList.value,
                        )
                    }
                }

            }
        }

    }
}
