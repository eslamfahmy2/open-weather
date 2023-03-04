package com.testapp.domain.state

import com.testapp.domain.models.Weather

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

enum class SearchSuggestionState {
    OPENED,
    CLOSED
}

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val weather: Weather) : HomeScreenState()
    data class Error(val msg: String?) : HomeScreenState()
}