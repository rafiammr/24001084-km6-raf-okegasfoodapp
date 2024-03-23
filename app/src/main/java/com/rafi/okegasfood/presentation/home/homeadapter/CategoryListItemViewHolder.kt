package com.rafi.okegasfood.presentation.home.homeadapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rafi.okegasfood.base.ViewHolderBinder
import com.rafi.okegasfood.data.model.Category
import com.rafi.okegasfood.databinding.ItemCategoryBinding

class CategoryListItemViewHolder (
    private val binding: ItemCategoryBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        item.let {
            setCategoryImage(it.imgUrl)
            binding.tvCategoryName.text = it.name
        }
    }

    private fun setCategoryImage(image: String?) {
        image?.let { binding.ivCategoryImg.load(it) }
    }
}