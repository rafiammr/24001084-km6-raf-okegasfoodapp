package com.rafi.okegasfood.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rafi.okegasfood.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {

    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun deleteAllCart(){
        viewModelScope.launch{
            cartRepository.deleteAllCart()
        }
    }
}