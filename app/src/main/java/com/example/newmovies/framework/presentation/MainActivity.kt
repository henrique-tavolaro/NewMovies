package com.example.newmovies.framework.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.newmovies.business.interactors.SearchMovie
import com.example.newmovies.framework.presentation.state.MovieStateEvent
import com.example.newmovies.framework.presentation.ui.theme.NewMoviesTheme
import com.example.newmovies.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            NewMoviesTheme {
            // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
            val movieList = viewModel.movieList.value
            Button(onClick = {
                viewModel.getMovie("batmano")
                Log.d("debugtag2", movieList.toString())
            }) {
                Text(text = "click")

            }
//                }
        }
    }
//    }
}

