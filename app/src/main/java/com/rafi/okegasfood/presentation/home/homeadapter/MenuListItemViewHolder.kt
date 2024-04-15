package com.rafi.okegasfood.presentation.home.homeadapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rafi.okegasfood.base.ViewHolderBinder
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.databinding.ItemMenuListBinding
import com.rafi.okegasfood.utils.toIndonesianFormat

class MenuListItemViewHolder(
    private val binding: ItemMenuListBinding,
    private val listener: OnItemClickedListener<Menu>
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {

    override fun bind(item: Menu) {
        item.let {
            binding.ivMenuImg.load(item.imgUrl)
            binding.tvMenuName.text = item.nameMenu
            binding.tvMenuPrice.text = item.price.toIndonesianFormat()
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }

}
