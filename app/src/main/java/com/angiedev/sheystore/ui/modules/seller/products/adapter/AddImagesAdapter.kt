package com.angiedev.sheystore.ui.modules.seller.products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemPhotoBinding

class AddImagesAdapter (
    private val photos: List<String>,
    private val onPhotoSelected: (String) -> Unit
): RecyclerView.Adapter<AddImagesAdapter.AddImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AddImagesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
    )

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: AddImagesViewHolder, position: Int) {
        holder.render(photos[position])
    }

    inner class AddImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPhotoBinding.bind(view)

        fun render(color: String) {
            binding.root.setOnClickListener {
                //notifyItemChanged()
                onPhotoSelected(color)
            }
        }

    }
}