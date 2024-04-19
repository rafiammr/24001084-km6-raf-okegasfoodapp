package com.rafi.okegasfood.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafi.okegasfood.data.repository.UserRepository

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun getCurrentUser() = repository.getCurrentUser()

    fun doLogout() {
        repository.doLogout()
    }
}