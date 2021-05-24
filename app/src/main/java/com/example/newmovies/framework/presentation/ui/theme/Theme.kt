package com.example.newmovies.framework.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.newmovies.framework.presentation.composables.CircularIndeterminateProgressBar
import com.example.newmovies.framework.presentation.composables.ConnectivityMonitor

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@ExperimentalComposeUiApi
@Composable
fun NewMoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isNetworkAvailable: Boolean,
    content: @Composable() () -> Unit
) {

//
        MaterialTheme(
            colors = if (darkTheme) DarkColorPalette else LightColorPalette,
            typography = Typography,
            shapes = Shapes,
//            content = content
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = if (!darkTheme) Color.LightGray else Color.Black)
            ) {
                Column{
                    ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                    content()
                }
//                CircularIndeterminateProgressBar(isDisplayed = displayProgressBar, 0.3f)
            }
        }
}