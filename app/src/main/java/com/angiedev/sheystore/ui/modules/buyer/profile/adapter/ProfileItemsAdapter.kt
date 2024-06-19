package com.angiedev.sheystore.ui.modules.buyer.profile.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemProfileBinding
import com.angiedev.sheystore.ui.modules.buyer.profile.data.GetProfileItems
import com.angiedev.sheystore.ui.modules.buyer.profile.data.ProfileItem
import com.angiedev.sheystore.ui.modules.buyer.profile.data.ProfileItemsType

class ProfileItemsAdapter(
    private val profileItemsListener: ProfileItemsListener
) : RecyclerView.Adapter<ProfileItemsAdapter.ProfileItemsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProfileItemsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
    )

    override fun getItemCount() = GetProfileItems.items.size

    override fun onBindViewHolder(holder: ProfileItemsViewHolder, position: Int) {
        holder.render(GetProfileItems.items[position])
    }

    inner class ProfileItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemProfileBinding.bind(view)

        fun render(item: ProfileItem){
            with(binding) {
                itemProfileStartIcon.setImageResource(item.startIcon)
                itemProfileEndIcon.setImageResource(item.endIcon)
                itemProfileName.text = item.title

                if(item.type == ProfileItemsType.Logout) {
                    itemProfileEndIcon.setColorFilter(Color.RED)
                    itemProfileName.setTextColor(Color.RED)
                    itemProfileStartIcon.setColorFilter(Color.RED)
                }

                root.setOnClickListener {
                    profileItemsListener.onProfileItemClickListener(item)
                }
            }
        }

    }
}