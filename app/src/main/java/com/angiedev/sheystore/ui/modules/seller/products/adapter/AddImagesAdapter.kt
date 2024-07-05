package com.angiedev.sheystore.ui.modules.seller.products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemPhotoBinding
import com.bumptech.glide.Glide
import java.net.URI

class AddImagesAdapter (
    private val onPhotoSelected: (String) -> Unit
): RecyclerView.Adapter<AddImagesAdapter.AddImagesViewHolder>() {

    private val photosList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AddImagesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
    )

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: AddImagesViewHolder, position: Int) {
        holder.render(photosList[position])
    }

    /*fun submitList(photos: List<String>) {
        photosList.clear()
        photosList.addAll(photos)
        notifyDataSetChanged()
    }*/

    fun addPhoto(photo: String) {
        photosList.add(photo)
        notifyItemInserted(photosList.size - 1)
    }

    inner class AddImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPhotoBinding.bind(view)

        fun render(photo: String) {

            if (isUrl(photo)) {
                Glide.with(itemView.context)
                    .load(photo)
                    .into(binding.itemImageView)
            } else {
                Glide.with(itemView.context)
                    .load(photo.toUri())
                    .into(binding.itemImageView)
            }

            binding.itemImageViewClose.setOnClickListener {
                onPhotoSelected(photo)
            }
        }

        fun isUrl(text: String): Boolean {
            val urlRegex =
                Regex("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
            return urlRegex.matches(text)
        }
    }
}