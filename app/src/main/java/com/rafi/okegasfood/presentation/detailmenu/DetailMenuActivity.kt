package com.rafi.okegasfood.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.datasource.cart.CartDataSource
import com.rafi.okegasfood.data.datasource.cart.CartDatabaseDataSource
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.data.repository.CartRepository
import com.rafi.okegasfood.data.repository.CartRepositoryImpl
import com.rafi.okegasfood.data.source.local.database.AppDatabase
import com.rafi.okegasfood.databinding.ActivityDetailMenuBinding
import com.rafi.okegasfood.utils.GenericViewModelFactory
import com.rafi.okegasfood.utils.hideKeyboard
import com.rafi.okegasfood.utils.proceedWhen
import com.rafi.okegasfood.utils.toIndonesianFormat

class DetailMenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRAS_MENU = "EXTRAS_MENU"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRAS_MENU, menu)
            context.startActivity(intent)
        }
    }

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, rp)
        )

        GenericViewModelFactory.create(DetailMenuViewModel(intent?.extras, rp))

    }
    private var totalItem: Int = 1
    private var totalPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(viewModel.menu)
        observeData(viewModel.menu)
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.layoutDetailMenu.layoutDetailMenuHeader.ivDetailMenuBg.load(item.imgUrl) {
                crossfade(true)
            }
            binding.layoutDetailMenu.layoutDetailMenuHeader.tvDetailMenuName.text = item.name
            binding.layoutDetailMenu.layoutDetailMenuHeader.tvDetailMenuPrice.text =
                item.price.toIndonesianFormat()
            binding.layoutDetailMenu.layoutDetailMenuHeader.tvDetailMenuDesc.text = item.shortDesc
            binding.layoutDetailMenu.layoutDetailLocation.tvDetailLocation.text = item.location
            binding.layoutDetailMenu.layoutDetailLocation.tvLocation.setOnClickListener {
                goGmaps(menu)
            }

            binding.layoutDetailMenu.layoutDetailLocation.tvDetailLocation.setOnClickListener {
                goGmaps(menu)
            }
            binding.layoutDetailMenu.layoutDetailMenuHeader.ibBack.setOnClickListener {
                onBackPressed()
            }
            binding.layoutButtonAddToCart.btnSubItem.setOnClickListener {
                viewModel.subItem()
            }
            binding.layoutButtonAddToCart.btnAddItem.setOnClickListener {
                viewModel.addItem()
            }
            binding.layoutButtonAddToCart.btnAddToCart.setOnClickListener {
                val notes = binding.layoutButtonAddToCart.editTextNotes.text.toString()
                hideKeyboard()
                addMenuCart(notes)
            }
        }
    }

    private fun addMenuCart(notes: String?) {
        viewModel.addToCart(notes).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Add Menu to cart succes", Toast.LENGTH_SHORT).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, "Add Menu to cart failed", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun observeData(menu: Menu?) {
        viewModel.totalItemMenu.observe(this) { item ->
            totalItem = item
            totalPrice = (totalItem * (menu?.price ?: 0).toDouble())
            binding.layoutButtonAddToCart.tvTotalItem.text = totalItem.toString()
            binding.layoutButtonAddToCart.btnAddToCart.text =
                getString(R.string.button_add_to_cart, totalPrice.toIndonesianFormat().toString())
        }
    }

    private fun goGmaps(menu: Menu) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(menu.urlLocation))
        startActivity(intent)
    }
}
