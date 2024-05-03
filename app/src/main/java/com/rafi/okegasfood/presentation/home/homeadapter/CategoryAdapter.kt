package com.rafi.okegasfood.presentation.home.homeadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rafi.okegasfood.data.model.Category
import com.rafi.okegasfood.databinding.ItemCategoryBinding

class CategoryAdapter(private val itemClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ItemCategoryViewHolder>() {
    private var asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Category>() {
                override fun areItemsTheSame(
                    oldItem: Category,
                    newItem: Category,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Category,
                    newItem: Category,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(items: List<Category>) {
        asyncDataDiffer.submitList(items)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemCategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ItemCategoryViewHolder(binding, itemClick)
    }

    // counting the data size
    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ItemCategoryViewHolder,
        position: Int,
    ) {
        holder.bindView(asyncDataDiffer.currentList[position])
    }

    class ItemCategoryViewHolder(
        private val binding: ItemCategoryBinding,
        val itemClick: (Category) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Category) {
            with(item) {
                binding.ivCategoryImg.load(item.imgUrl) {
                    crossfade(true)
                }
                binding.tvCategoryName.text = item.nameCategory
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
