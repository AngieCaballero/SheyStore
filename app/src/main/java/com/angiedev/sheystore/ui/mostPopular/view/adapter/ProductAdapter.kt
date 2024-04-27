package com.angiedev.sheystore.ui.mostPopular.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.entities.ProductEntity
import com.angiedev.sheystore.databinding.ItemProductBinding
import com.angiedev.sheystore.ui.product.adapter.ProductItemListener
import com.bumptech.glide.Glide

class ProductAdapter(
    private val productItemListener: ProductItemListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList = mutableListOf<ProductEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    )

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.render(productList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterBy(newList: List<ProductEntity>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemProductBinding.bind(view)

        fun render(productEntity: ProductEntity) {
            with(binding) {
                itemProductCategory.text = productEntity.category
                itemProductName.text = productEntity.name
                itemProductPrice.text = productEntity.price.toString()
                itemProductRate.text = productEntity.rate
                Glide.with(root.context)
                    .load(productEntity.image)
                    .error(R.drawable.product_hint)
                    .into(itemProductImage)

                binding.root.setOnClickListener {
                    productItemListener.onClickItem(productEntity)
                }
            }
        }
    }
}