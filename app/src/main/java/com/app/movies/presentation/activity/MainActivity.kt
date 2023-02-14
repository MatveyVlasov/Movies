package com.app.movies.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.movies.R
import com.app.movies.databinding.ActivityMainBinding
import com.app.movies.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController

    override fun createBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    fun setLoadingState(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}