package com.angiedev.sheystore.ui.home.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.entities.CategoryEntity
import com.angiedev.sheystore.databinding.ItemCategoryBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val categoryList = mutableListOf<CategoryEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
    )

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.render(categoryList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<CategoryEntity>) {
        categoryList.clear()
        categoryList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemCategoryBinding.bind(view)

        fun render(categoryEntity: CategoryEntity) {
            binding.itemCategoryName.text = categoryEntity.name
            Glide.with(binding.root.context)
                .load(categoryEntity.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.itemCategoryImage)
        }
    }
}