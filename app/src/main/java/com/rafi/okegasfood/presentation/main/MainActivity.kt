package com.rafi.okegasfood.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.datasource.AuthDataSource
import com.rafi.okegasfood.data.datasource.FirebaseAuthDataSource
import com.rafi.okegasfood.data.repository.UserRepository
import com.rafi.okegasfood.data.repository.UserRepositoryImpl
import com.rafi.okegasfood.data.source.firebase.FirebaseService
import com.rafi.okegasfood.data.source.firebase.FirebaseServiceImpl
import com.rafi.okegasfood.databinding.ActivityMainBinding
import com.rafi.okegasfood.presentation.login.LoginActivity
import com.rafi.okegasfood.utils.GenericViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        val source: FirebaseService = FirebaseServiceImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(source)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(MainViewModel(repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.menu_tab_profile -> {
                    lifecycleScope.launch {
                        delay(500)
                        if (!viewModel.isUserLoggedIn()) {
                            Log.d("UserLoggedIn", "User not logged in. Navigating to login.")
                            navigateToLogin()
                            controller.navigate(R.id.menu_tab_home)
                        }
                        else{
                            Log.d("UserLoggedIn", "User is logged in. Username: ${viewModel.getCurrentUser()}")
                        }
                    }

                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }
}