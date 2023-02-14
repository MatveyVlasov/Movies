package com.app.movies.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.movies.domain.model.onError
import com.app.movies.domain.model.onSuccess
import com.app.movies.domain.usecase.GetPopularMoviesUseCase
import com.app.movies.presentation.model.ErrorItem
import com.app.movies.presentation.model.LoadingItem
import com.app.movies.presentation.model.UIState
import com.app.movies.presentation.screen.home.mapper.toMovieItem
import com.app.movies.presentation.screen.home.model.HomeScreenEvent
import com.app.movies.presentation.screen.home.model.HomeScreenUIState
import com.app.movies.util.delegateadapter.DelegateAdapterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    val uiState: StateFlow<UIState<HomeScreenUIState>>
        get() = _uiState

    val event: SharedFlow<HomeScreenEvent>
        get() = _event

    private val _uiState = MutableStateFlow<UIState<HomeScreenUIState>>(UIState.Loading)
    private val _event = MutableSharedFlow<HomeScreenEvent>()

    private var screenUIState = HomeScreenUIState()

    private var job: Job? = null

    init {
        updateList()
    }

    fun updateList() {
        if (job?.isActive == true) return

        postItem(LoadingItem)

        job = viewModelScope.launch {
            getPopularMoviesUseCase(page = screenUIState.page).onSuccess {
                Timber.i("Here we got $it")
                updateScreenState(screenUIState.copy(page = screenUIState.page + 1))
                postListItems(it.map { movie -> movie.toMovieItem() })
            }.onError {
                postItem(ErrorItem(it.message))
            }
        }
    }

    private fun postItem(data: DelegateAdapterItem) = postListItems(listOf(data))

    private fun postListItems(data: List<DelegateAdapterItem>) {
        updateScreenState(
            screenUIState.copy(
                movies = screenUIState.movies.toMutableList().apply {
                    removeAll { it is LoadingItem || it is ErrorItem }
                    addAll(data)
                }
            )
        )
    }

    private fun updateScreenState(state: HomeScreenUIState) {
        screenUIState = state
        _uiState.value = UIState.Success(screenUIState)
    }

    private fun emitEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}