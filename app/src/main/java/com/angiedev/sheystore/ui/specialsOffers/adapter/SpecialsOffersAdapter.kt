package com.angiedev.sheystore.ui.specialsOffers.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.domain.entities.specialsOffers.SpecialOfferEntity
import com.angiedev.sheystore.databinding.ItemSpecialsOffersBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class SpecialsOffersAdapter : RecyclerView.Adapter<SpecialsOffersAdapter.SpecialsOffersViewHolder>() {

    private val specialsOffersList = mutableListOf<SpecialOfferEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SpecialsOffersViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_specials_offers, parent, false)
    )
    override fun getItemCount() = specialsOffersList.size

    override fun onBindViewHolder(holder: SpecialsOffersViewHolder, position: Int) {
        holder.render(specialsOffersList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<SpecialOfferEntity>) {
        specialsOffersList.clear()
        specialsOffersList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class SpecialsOffersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemSpecialsOffersBinding.bind(view)

        fun render(specialsOffers: SpecialOfferEntity) {
            with(binding) {
                specialsOffersDiscount.text = specialsOffers.percentDiscount
                specialsOffersTitle.text = specialsOffers.title
                specialsOffersShortDescription.text = specialsOffers.description
                Glide.with(root.context)
                    .load(specialsOffers.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(specialsOffersImage)
            }
        }
    }
}
