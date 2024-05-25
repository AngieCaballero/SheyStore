package com.angiedev.sheystore.ui.profile.adapter

import com.angiedev.sheystore.ui.profile.data.ProfileItem

interface ProfileItemsListener {
    fun onProfileItemClickListener(item: ProfileItem)
}