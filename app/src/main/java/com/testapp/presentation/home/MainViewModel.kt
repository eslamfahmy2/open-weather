package com.testapp.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapp.domain.repository.AuthRepository
import com.testapp.domain.state.HomeScreenState
import com.testapp.domain.state.SearchWidgetState
import com.testapp.domain.state.StateData
import com.testapp.utils.launchIdling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    //data holders
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _previousSearchList: MutableList<String> =
        mutableStateListOf("new york", "cairo", "liverpool")
    val previousSearchList: MutableList<String> = _previousSearchList

    //private home screen state
    private var _weatherFlow: StateFlow<HomeScreenState> =
        getWeatherForecastByCityName("london")

    //public home screen state
    val weatherFlow: StateFlow<HomeScreenState> get() = _weatherFlow

    //update search view state [ closed , opened]
    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    // update search query text
    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    //trigger search
    fun search() {
        viewModelScope.launchIdling {
            _weatherFlow = getWeatherForecastByCityName(_searchTextState.value)
        }
    }

    //get weather data as state
    private fun getWeatherForecastByCityName(city: String): StateFlow<HomeScreenState> {
        return authRepository.getWeatherByQuery(city).map {
            when (it.status) {
                StateData.DataStatus.SUCCESS -> {
                    it.data?.let { weather ->
                        saveSearchKeyword(_searchTextState.value.lowercase())
                        HomeScreenState.Success(weather)
                    } ?: HomeScreenState.Error(msg = it.error?.message)
                }
                StateData.DataStatus.ERROR -> {
                    HomeScreenState.Error(msg = it.error?.message)
                }
                StateData.DataStatus.LOADING -> {
                    HomeScreenState.Loading
                }
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = HomeScreenState.Loading
        )
    }

    //add search key word to previous search list
    private fun saveSearchKeyword(value: String) {
        if (!_previousSearchList.contains(value))
            _previousSearchList.add(value)
    }

}