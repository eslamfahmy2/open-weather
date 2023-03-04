package com.testapp.presentation.components.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.testapp.domain.state.SearchWidgetState
import com.testapp.presentation.home.HomeScreen
import com.testapp.presentation.home.MainViewModel
import com.testapp.utils.collectAsStateLifecycleAware


sealed class Destinations(
    val route: String
) {
    object HomeScreen : Destinations("home")
}

// Adds home screen to `this` NavGraphBuilder
fun NavGraphBuilder.homeDestination() {
    composable(Destinations.HomeScreen.route) {
        // The ViewModel as a screen level state holder produces the screen
        // UI state and handles business logic for the screen
        val mainViewModel: MainViewModel = hiltViewModel()

        val uiState = mainViewModel.weatherFlow.collectAsStateLifecycleAware().value
        val searchBarState by mainViewModel.searchWidgetState
        val searchText by mainViewModel.searchTextState

        val previousSearchList = remember {
            mainViewModel.previousSearchList
        }.filter { it.lowercase().contains(searchText.lowercase()) }

        HomeScreen(
            uiState = uiState,
            searchBarState = searchBarState,
            searchText = searchText,
            onTextChange = {
                mainViewModel.updateSearchTextState(newValue = it)
            },
            onCloseClicked = {
                mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
            },
            onSearchClicked = {
                mainViewModel.updateSearchTextState(newValue = it)
                mainViewModel.search()
            },
            onSearchTriggered = {
                mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
            },
            previousSearchList = previousSearchList
        )
    }

}
