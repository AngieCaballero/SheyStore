package com.angiedev.sheystore.ui.modules.buyer.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemImagesProductDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ProductDetailsImagesViewPagerAdapter(
    private val images: List<String>
) : RecyclerView.Adapter<ProductDetailsImagesViewPagerAdapter.ProductDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductDetailsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_images_product_details, parent, false)
    )

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ProductDetailsViewHolder, position: Int) {
        holder.render(images[position])
    }

    inner class ProductDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemImagesProductDetailsBinding.bind(view)

        fun render(image: String) {
            Glide.with(binding.root.context)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.itemProductDetailsImageView)
        }
    }
}
