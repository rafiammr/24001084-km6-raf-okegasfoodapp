package com.rafi.okegasfood.presentation.detailmenu

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafi.okegasfood.data.model.Menu

class DetailMenuViewModel(
    private val extras: Bundle?,
) : ViewModel() {
    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRAS_MENU)
    private val totalMenu = MutableLiveData<Int>()
    val totalItemMenu: LiveData<Int> get() = totalMenu

    init {
        totalMenu.value = 1
    }

    fun addItem() {
        val count = totalMenu.value ?: 1
        totalMenu.value = count + 1
    }

    fun subItem() {
        val count = totalMenu.value ?: 1
        if (count > 1) {
            totalMenu.value = count - 1
        }
    }
}