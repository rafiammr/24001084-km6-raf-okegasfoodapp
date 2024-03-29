package com.rafi.okegasfood.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rafi.okegasfood.R
import com.rafi.okegasfood.base.ViewHolderBinder
import com.rafi.okegasfood.data.model.Cart
import com.rafi.okegasfood.databinding.ItemCartListBinding
import com.rafi.okegasfood.databinding.ItemCartListOrderBinding
import com.rafi.okegasfood.utils.doneEditing
import com.rafi.okegasfood.utils.toIndonesianFormat

class CartListAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(
                oldItem: Cart,
                newItem: Cart
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.menuId == newItem.menuId
            }

            override fun areContentsTheSame(
                oldItem: Cart,
                newItem: Cart
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun submitData(data: List<Cart>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (cartListener != null) CartViewHolder(
            ItemCartListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        ) else CartOrderViewHolder(
            ItemCartListOrderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
    }

}

class CartViewHolder(
    private val binding: ItemCartListBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivMenuCart.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvTotalItem.text = item.itemQuantity.toString()
            tvCartMenuName.text = item.menuName
            val totalPrice = item.itemQuantity * item.menuPrice
            tvCartMenuPrice.text = (totalPrice.toIndonesianFormat()).toString()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotesCart.setText(item.itemNotes)
        binding.etNotesCart.doneEditing {
            binding.etNotesCart.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etNotesCart.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding) {
            ibSubItem.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            ibAddItem.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            ibDelete.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
        }
    }
}

class CartOrderViewHolder(
    private val binding: ItemCartListOrderBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivMenuCart.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvTotalItem.text =
                itemView.rootView.context.getString(
                    R.string.total_quantity,
                    item.itemQuantity.toString()
                )
            tvCartMenuName.text = item.menuName
            tvCartMenuPrice.text = item.menuPrice.toIndonesianFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNotesCart.text = item.itemNotes
    }

}

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}