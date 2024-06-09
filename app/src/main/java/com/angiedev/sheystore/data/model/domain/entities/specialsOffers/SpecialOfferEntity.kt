package com.angiedev.sheystore.data.model.domain.entities.specialsOffers

import com.angiedev.sheystore.data.model.remote.response.dto.specialsOffers.SpecialOfferDTO
import com.google.gson.annotations.SerializedName

data class SpecialOfferEntity(
    val id: Int,
    val percentDiscount: String,
    val description: String,
    val image: String,
    val title: String
) {
    constructor(specialOfferDTO: SpecialOfferDTO?) : this(
        id = specialOfferDTO?.id?.toInt() ?: 0,
        percentDiscount = specialOfferDTO?.percentDiscount.orEmpty(),
        description = specialOfferDTO?.description.orEmpty(),
        image = specialOfferDTO?.image.orEmpty(),
        title = specialOfferDTO?.title.orEmpty()
    )
}
