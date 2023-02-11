package com.app.movies.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.app.movies.databinding.FragmentHomeBinding
import com.app.movies.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    override fun createBinding(inflater: LayoutInflater) = FragmentHomeBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

    }
}