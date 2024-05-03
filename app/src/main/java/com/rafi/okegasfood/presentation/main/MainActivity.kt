package com.rafi.okegasfood.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rafi.okegasfood.R
import com.rafi.okegasfood.databinding.ActivityMainBinding
import com.rafi.okegasfood.presentation.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

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
                        if (!mainViewModel.isUserLoggedIn()) {
                            Log.d("UserLoggedIn", "User not logged in. Navigating to login.")
                            navigateToLogin()
                            controller.popBackStack(R.id.menu_tab_home, false)
                        } else {
                            Log.d("UserLoggedIn", "User is logged in. Username: ${mainViewModel.getCurrentUser()}")
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
