package com.angiedev.sheystore.ui.specialsOffers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemSpecialsOffersBinding

class SpecialsOffersAdapter : RecyclerView.Adapter<SpecialsOffersAdapter.SpecialsOffersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SpecialsOffersViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_specials_offers, parent, false)
    )
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SpecialsOffersViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class SpecialsOffersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemSpecialsOffersBinding.bind(view)

        fun render() {

        }
    }
}
