package com.angiedev.sheystore.ui.profile.data

sealed class ProfileItemsType {

    data object EditProfile : ProfileItemsType()
    data object Address : ProfileItemsType()
    data object Notification : ProfileItemsType()
    data object Payment : ProfileItemsType()
    data object Security : ProfileItemsType()
    data object Order : ProfileItemsType()
    data object PrivacyPolicy : ProfileItemsType()
    data object HelpCenter : ProfileItemsType()
    data object InviteFriends : ProfileItemsType()
    data object Logout : ProfileItemsType()
}
