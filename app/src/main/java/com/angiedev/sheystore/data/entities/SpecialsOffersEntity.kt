package com.angiedev.sheystore.data.entities

import com.angiedev.sheystore.data.model.remote.response.SpecialsOffersResponse

data class SpecialsOffersEntity (
    val id: Int,
    val percentDiscount: String,
    val description: String,
    val image: String,
    val title: String
) {
    constructor(specialsOffersResponse: SpecialsOffersResponse?) : this(
        id = specialsOffersResponse?.id?.integerValue?.toInt() ?: 0,
        percentDiscount = specialsOffersResponse?.percentDiscount?.stringValue.orEmpty(),
        description = specialsOffersResponse?.description?.stringValue.orEmpty(),
        image = specialsOffersResponse?.image?.stringValue.orEmpty(),
        title = specialsOffersResponse?.title?.stringValue.orEmpty()
    )
}