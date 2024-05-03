package com.rafi.okegasfood.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.okegasfood.data.repository.UserRepository
import com.rafi.okegasfood.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    val isEditMode = MutableLiveData(false)

    private val _changeProfileResult = MutableLiveData<ResultWrapper<Boolean>>()
    val changeProfileResult: LiveData<ResultWrapper<Boolean>>
        get() = _changeProfileResult

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun getCurrentUser() = repository.getCurrentUser()

    fun updateFullName(fullName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProfile(fullname = fullName).collect {
                _changeProfileResult.postValue(it)
            }
        }
    }

    fun doLogout() {
        repository.doLogout()
    }
}
