package com.rafi.okegasfood.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafi.okegasfood.data.model.Profile

class ProfileViewModel : ViewModel() {
    val profileData = MutableLiveData(
        Profile(
            name = "Patrick Wanggai",
            username = "Patrick",
            email = "Patrickwanggai0@gmail.com",
            noTelephone = 628121313873,
            profileImg = "https://i.pinimg.com/564x/4c/da/a6/4cdaa617dc5ac7ecab3b74aa7154fc37.jpg"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}