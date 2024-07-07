package com.angiedev.sheystore.ui.modules.buyer.profile.data

import com.angiedev.sheystore.R

data class ProfileItem(
    val id: Int,
    val startIcon: Int,
    val endIcon: Int,
    val title: String,
    val type: ProfileItemsType
)

object GetProfileItems {
    val items = listOf(
        ProfileItem(
            id = 1,
            startIcon = R.drawable.ic_outline_person,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Editar Perfil",
            type = ProfileItemsType.EditProfile
        ),
        ProfileItem(
            id = 2,
            startIcon = R.drawable.ic_location,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Dirección",
            type = ProfileItemsType.Address
        ),
        ProfileItem(
            id = 3,
            startIcon = R.drawable.ic_notifications,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Notificación",
            type = ProfileItemsType.Notification
        ),
        ProfileItem(
            id = 4,
            startIcon = R.drawable.ic_payment,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Pagos",
            type = ProfileItemsType.Payment
        ),
        ProfileItem(
            id = 5,
            startIcon = R.drawable.ic_security,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Seguridad",
            type = ProfileItemsType.Security
        ),
        ProfileItem(
            id = 6,
            startIcon = R.drawable.ic_cart,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Pedidos",
            type = ProfileItemsType.Order
        ),
        ProfileItem(
            id = 7,
            startIcon = R.drawable.ic_privacy_policy,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Políticas de Privacidad",
            type = ProfileItemsType.PrivacyPolicy
        ),
        ProfileItem(
            id = 8,
            startIcon = R.drawable.ic_information,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Centro de Ayuda",
            type = ProfileItemsType.HelpCenter
        ),
        ProfileItem(
            id = 9,
            startIcon = R.drawable.ic_person_group,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Invitar Amigos",
            type = ProfileItemsType.InviteFriends
        ),
        ProfileItem(
            id = 9,
            startIcon = R.drawable.ic_users_config,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Configuraciones avanzadas",
            type = ProfileItemsType.UsersConfiguration
        ),
        ProfileItem(
            id = 10,
            startIcon = R.drawable.ic_logout,
            endIcon = R.drawable.ic_arrow_angle_right,
            title = "Cerrar Sesión",
            type = ProfileItemsType.Logout
        )
    )
}