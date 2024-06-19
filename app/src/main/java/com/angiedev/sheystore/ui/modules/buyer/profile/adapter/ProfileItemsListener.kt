package com.angiedev.sheystore.ui.modules.buyer.profile.adapter

import com.angiedev.sheystore.ui.modules.buyer.profile.data.ProfileItem

interface ProfileItemsListener {
    fun onProfileItemClickListener(item: ProfileItem)
}