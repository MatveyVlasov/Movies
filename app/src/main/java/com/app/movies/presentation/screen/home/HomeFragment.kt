package com.app.movies.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.movies.databinding.FragmentHomeBinding
import com.app.movies.presentation.base.BaseFragment
import com.app.movies.util.delegateadapter.CompositeAdapter
import com.app.movies.presentation.adapter.ErrorDelegateAdapter
import com.app.movies.presentation.adapter.LoadingDelegateAdapter
import com.app.movies.presentation.model.onSuccess
import com.app.movies.presentation.screen.home.adapter.MovieDelegateAdapter
import com.app.movies.presentation.screen.home.model.HomeScreenEvent
import com.app.movies.util.setupBottomNavigation
import com.app.movies.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private val moviesAdapter by lazy {
        CompositeAdapter.Builder()
            .setOnUpdateCallback(viewModel::updateList)
            .add(ErrorDelegateAdapter(viewModel::updateList))
            .add(LoadingDelegateAdapter())
            .add(MovieDelegateAdapter(onClick = {}))
            .build()
    }

    override fun createBinding(inflater: LayoutInflater) = FragmentHomeBinding.inflate(inflater)

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

        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        it.onSuccess { data ->
                            moviesAdapter.submitList(data.movies)
                        }
                    }
                }

                launch {
                    viewModel.event.collect { handleEvent(it) }
                }
            }
        }
    }

    private fun handleEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ShowSnackbar -> showSnackbar(message = event.message)
            is HomeScreenEvent.ShowSnackbarByRes -> showSnackbar(message = event.message)
            is HomeScreenEvent.Loading -> Unit
        }
        setLoadingState(event is HomeScreenEvent.Loading)
    }
}