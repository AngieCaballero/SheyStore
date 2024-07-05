package com.angiedev.sheystore.ui.modules.seller.products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemSellerProductBinding
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class SellerProductAdapter (
    val listener: SellerProductListener,
): RecyclerView.Adapter<SellerProductAdapter.SellerProductViewHolder>() {

    private val products = mutableListOf<ProductEntity>()

    interface SellerProductListener {
        fun onDeleteButtonClick(productId: Int)
        fun onEditButtonClick(productId: Int)
        fun onDetailButtonClick(productId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SellerProductViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_seller_product, parent, false)
    )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: SellerProductViewHolder, position: Int) {
        holder.render(products[position])
    }

    fun submitList(newProducts: List<ProductEntity>) {
        this.products.clear()
        this.products.addAll(newProducts)
        notifyDataSetChanged()
    }

    inner class SellerProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSellerProductBinding.bind(view)

        fun render(product: ProductEntity) {
            binding.productDeleteButton.setOnClickListener {
                listener.onDeleteButtonClick(product.id)
            }
            binding.productEditButton.setOnClickListener {
                listener.onEditButtonClick(product.id)
            }
            binding.productSeeDetailsButton.setOnClickListener {
                listener.onDetailButtonClick(product.id)
            }

            binding.productName.text = product.name
            binding.productRate.text = product.rate
            binding.productPrice.text = product.price.toString()
            Glide.with(itemView.context)
                .load(product.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.product_hint)
                .into(binding.productImage)
        }

    }
}