package com.example.newmovies.framework.presentation.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Search : Screen("search", "Search", Icons.Default.Search)
    object ToWatchList : Screen("toWatchList", "ToWatchList", Icons.Default.WatchLater)
    object Watched : Screen("watched", "Watched", Icons.Default.Done)
}
