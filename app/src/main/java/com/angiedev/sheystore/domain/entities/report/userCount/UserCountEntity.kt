package com.angiedev.sheystore.domain.entities.report.userCount

import com.angiedev.sheystore.data.model.remote.response.dto.report.userCount.UserCountDTO
import java.util.Date

data class UserCountEntity(
    val count: Int,
    val date: Date
) {
    constructor(userCountDTO: UserCountDTO?) : this(
        count = userCountDTO?.count ?: 0,
        date = userCountDTO?.date ?: Date()
    )
}
