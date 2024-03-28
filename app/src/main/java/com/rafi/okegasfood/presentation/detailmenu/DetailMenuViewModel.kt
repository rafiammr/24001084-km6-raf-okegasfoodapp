package com.rafi.okegasfood.presentation.detailmenu

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.asLiveData
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.data.repository.CartRepository
import com.rafi.okegasfood.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
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

    fun addToCart(notes: String?) : LiveData<ResultWrapper<Boolean>>{
        return menu?.let {
            val quantity = totalItemMenu.value ?: 1
            cartRepository.createCart(it, quantity, notes).asLiveData(Dispatchers.IO)
        }  ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Menu not found")))}
    }
}