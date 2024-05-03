package com.rafi.okegasfood.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.databinding.ActivityDetailMenuBinding
import com.rafi.okegasfood.utils.hideKeyboard
import com.rafi.okegasfood.utils.proceedWhen
import com.rafi.okegasfood.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {
    companion object {
        const val EXTRAS_MENU = "EXTRAS_MENU"

        fun startActivity(
            context: Context,
            menu: Menu,
        ) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRAS_MENU, menu)
            context.startActivity(intent)
        }
    }

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val detailMenuViewModel: DetailMenuViewModel by viewModel {
        parametersOf(intent.extras)
    }
    private var totalItem: Int = 1
    private var totalPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(detailMenuViewModel.menu)
        observeData(detailMenuViewModel.menu)
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.layoutDetailMenu.layoutDetailMenuHeader.ivDetailMenuBg.load(item.imgUrl) {
                crossfade(true)
            }
            binding.layoutDetailMenu.layoutDetailMenuHeader.tvDetailMenuName.text = item.nameMenu
            binding.layoutDetailMenu.layoutDetailMenuHeader.tvDetailMenuPrice.text =
                item.price.toIndonesianFormat()
            binding.layoutDetailMenu.layoutDetailMenuHeader.tvDetailMenuDesc.text = item.detail
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
                detailMenuViewModel.subItem()
            }
            binding.layoutButtonAddToCart.btnAddItem.setOnClickListener {
                detailMenuViewModel.addItem()
            }
            binding.layoutButtonAddToCart.btnAddToCart.setOnClickListener {
                val notes = binding.layoutButtonAddToCart.editTextNotes.text.toString()
                hideKeyboard()
                addMenuCart(notes)
            }
        }
    }

    private fun addMenuCart(notes: String?) {
        detailMenuViewModel.addToCart(notes).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_menu_to_cart_succes),
                        Toast.LENGTH_SHORT,
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_menu_to_cart_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    Toast.makeText(this, getString(R.string.text_loading), Toast.LENGTH_SHORT).show()
                },
            )
        }
    }

    private fun observeData(menu: Menu?) {
        detailMenuViewModel.totalItemMenu.observe(this) { item ->
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
