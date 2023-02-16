package com.app.movies.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.movies.domain.model.onError
import com.app.movies.domain.model.onSuccess
import com.app.movies.domain.usecase.SearchMoviesUseCase
import com.app.movies.presentation.model.ErrorItem
import com.app.movies.presentation.model.LoadingItem
import com.app.movies.presentation.model.UIState
import com.app.movies.presentation.screen.home.mapper.toMovieItem
import com.app.movies.presentation.screen.search.model.SearchScreenEvent
import com.app.movies.presentation.screen.search.model.SearchScreenUIState
import com.app.movies.util.delegateadapter.DelegateAdapterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    val uiState: StateFlow<UIState<SearchScreenUIState>>
        get() = _uiState

    val event: SharedFlow<SearchScreenEvent>
        get() = _event

    private val _uiState = MutableStateFlow<UIState<SearchScreenUIState>>(UIState.Loading)
    private val _event = MutableSharedFlow<SearchScreenEvent>()

    private var screenUIState = SearchScreenUIState()

    private var job: Job? = null

    init {
        updateList()
    }

    fun onSearchQueryChanged(searchQuery: String) {
        if (searchQuery == screenUIState.searchQuery) return
        updateScreenState(screenUIState.copy(searchQuery = searchQuery))
    }

    fun updateList() {
        if (job?.isActive == true) return

        postItem(LoadingItem)

        job = viewModelScope.launch {
            searchMoviesUseCase(query = screenUIState.searchQuery).onSuccess {
                screenUIState = screenUIState.copy(movies = emptyList())
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

    private fun updateScreenState(state: SearchScreenUIState) {
        screenUIState = state
        _uiState.value = UIState.Success(screenUIState)
    }

    private fun emitEvent(event: SearchScreenEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}