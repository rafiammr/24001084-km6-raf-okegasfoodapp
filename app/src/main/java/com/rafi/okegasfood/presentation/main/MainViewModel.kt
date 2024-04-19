package com.rafi.okegasfood.presentation.main

import androidx.lifecycle.ViewModel
import com.rafi.okegasfood.data.repository.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    fun isUserLoggedIn(): Boolean = repository.isLoggedIn()

    fun getCurrentUser() = repository.getCurrentUser()
}