package com.app.movies.presentation.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.movies.databinding.FragmentSearchBinding
import com.app.movies.presentation.base.BaseFragment
import com.app.movies.util.delegateadapter.CompositeAdapter
import com.app.movies.presentation.adapter.ErrorDelegateAdapter
import com.app.movies.presentation.adapter.LoadingDelegateAdapter
import com.app.movies.presentation.model.onSuccess
import com.app.movies.presentation.adapter.MovieDelegateAdapter
import com.app.movies.presentation.screen.search.model.SearchScreenEvent
import com.app.movies.presentation.screen.search.model.SearchScreenUIState
import com.app.movies.util.onDone
import com.app.movies.util.setupBottomNavigation
import com.app.movies.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()

    private val moviesAdapter by lazy {
        CompositeAdapter.Builder()
            .add(ErrorDelegateAdapter(viewModel::updateList))
            .add(LoadingDelegateAdapter())
            .add(MovieDelegateAdapter(onClick = {}))
            .build()
    }

    override fun createBinding(inflater: LayoutInflater) = FragmentSearchBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        collectData()
    }

    private fun initViews() {

        setupBottomNavigation(true)
        binding.apply {
            rvMovies.adapter = moviesAdapter
        }
    }

    private fun initListeners() {
        binding.apply {

            etSearch.onDone { viewModel.updateList() }
            btnSearch.setOnClickListener { viewModel.updateList() }

            etSearch.addTextChangedListener { viewModel.onSearchQueryChanged(it.toString()) }
        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        it.onSuccess { data ->
                            moviesAdapter.submitList(data.movies)
                            renderUIState(data)
                        }
                    }
                }

                launch {
                    viewModel.event.collect { handleEvent(it) }
                }
            }
        }
    }

    private fun handleEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.ShowSnackbar -> showSnackbar(message = event.message)
            is SearchScreenEvent.ShowSnackbarByRes -> showSnackbar(message = event.message)
            is SearchScreenEvent.Loading -> Unit
        }
        setLoadingState(event is SearchScreenEvent.Loading)
    }

    private fun renderUIState(data: SearchScreenUIState) {
        binding.apply {
            if (!etSearch.isFocused) etSearch.setText(data.searchQuery)
        }

        setLoadingState(false)
    }
}