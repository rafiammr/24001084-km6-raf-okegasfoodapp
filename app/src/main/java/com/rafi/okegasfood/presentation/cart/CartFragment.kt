package com.rafi.okegasfood.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.datasource.AuthDataSource
import com.rafi.okegasfood.data.datasource.FirebaseAuthDataSource
import com.rafi.okegasfood.data.datasource.cart.CartDataSource
import com.rafi.okegasfood.data.datasource.cart.CartDatabaseDataSource
import com.rafi.okegasfood.data.model.Cart
import com.rafi.okegasfood.data.repository.CartRepository
import com.rafi.okegasfood.data.repository.CartRepositoryImpl
import com.rafi.okegasfood.data.repository.UserRepository
import com.rafi.okegasfood.data.repository.UserRepositoryImpl
import com.rafi.okegasfood.data.source.firebase.FirebaseService
import com.rafi.okegasfood.data.source.firebase.FirebaseServiceImpl
import com.rafi.okegasfood.data.source.local.database.AppDatabase
import com.rafi.okegasfood.databinding.FragmentCartBinding
import com.rafi.okegasfood.presentation.checkout.CheckoutActivity
import com.rafi.okegasfood.presentation.common.CartListAdapter
import com.rafi.okegasfood.presentation.common.CartListener
import com.rafi.okegasfood.presentation.login.LoginActivity
import com.rafi.okegasfood.utils.GenericViewModelFactory
import com.rafi.okegasfood.utils.hideKeyboard
import com.rafi.okegasfood.utils.proceedWhen
import com.rafi.okegasfood.utils.toIndonesianFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels {
        val source : FirebaseService = FirebaseServiceImpl()
        val dataSource : AuthDataSource = FirebaseAuthDataSource(source)
        val repository : UserRepository = UserRepositoryImpl(dataSource)
        val appDatabase = AppDatabase.getInstance(requireContext())
        val cartDataSource: CartDataSource = CartDatabaseDataSource(appDatabase.cartDao())
        val cartRepository: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(CartViewModel(cartRepository, repository))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNotes(cart)
                hideKeyboard()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnCheckout.setOnClickListener {
            checkIfUserLogin()
        }
    }

    private fun checkIfUserLogin() {
        lifecycleScope.launch {
            if (viewModel.isUserLoggedIn()) {
                navigateToCheckout()
            } else {
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }

    private fun navigateToCheckout() {
        startActivity(Intent(requireContext(), CheckoutActivity::class.java))
    }

    private fun observeData() {
        viewModel.getAllCarts().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = false
                    binding.btnCheckout.isEnabled = false
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = true
                    binding.btnCheckout.isEnabled = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvCartTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvCart.isVisible = false
                    binding.btnCheckout.isEnabled = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    binding.rvCart.isVisible = false
                    binding.btnCheckout.isEnabled = false
                    result.payload?.let { (carts, totalPrice) ->
                        binding.tvCartTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                }
            )
        }
    }

    private fun setupList() {
        binding.rvCart.adapter = this@CartFragment.adapter
    }

}