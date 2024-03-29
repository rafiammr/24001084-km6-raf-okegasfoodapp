package com.rafi.okegasfood.presentation.checkout

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.catnip.kokomputer.presentation.checkout.adapter.PriceListAdapter
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.datasource.cart.CartDataSource
import com.rafi.okegasfood.data.datasource.cart.CartDatabaseDataSource
import com.rafi.okegasfood.data.repository.CartRepository
import com.rafi.okegasfood.data.repository.CartRepositoryImpl
import com.rafi.okegasfood.utils.proceedWhen
import com.rafi.okegasfood.data.source.local.database.AppDatabase
import com.rafi.okegasfood.databinding.ActivityCheckoutBinding
import com.rafi.okegasfood.databinding.CustomDialogBinding
import com.rafi.okegasfood.presentation.common.CartListAdapter
import com.rafi.okegasfood.presentation.home.HomeFragment
import com.rafi.okegasfood.utils.GenericViewModelFactory
import com.rafi.okegasfood.utils.toIndonesianFormat

class CheckoutActivity : AppCompatActivity() {

    private val binding : ActivityCheckoutBinding by lazy{
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CheckoutViewModel(rp))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setOnClickListeners()
    }

    private fun customDialog() {
        val dialogBinding = CustomDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)

        dialogBinding.btnBackToHome.setOnClickListener {
            viewModel.deleteAllCart()
            finish()
        }
        dialog.create().show()
    }

    private fun setOnClickListeners() {
        binding.ivBack.setOnClickListener{
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            customDialog()
        }
    }


    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = true
                binding.layoutContent.rvCart.isVisible = true
                binding.cvSectionCheckout.isVisible = true
                result.payload?.let { (carts, priceItems, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvCartTotalPrice.text = totalPrice.toIndonesianFormat()
                    priceItemAdapter.submitData(priceItems)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionCheckout.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionCheckout.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                data.payload?.let { (_, _, totalPrice) ->
                    binding.tvCartTotalPrice.text = totalPrice.toIndonesianFormat()
                }
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionCheckout.isVisible = false
            })
        }
    }
}