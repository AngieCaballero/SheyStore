package com.angiedev.sheystore.data.model.remote.response.dto.specialsOffers

import com.google.gson.annotations.SerializedName

data class SpecialOfferResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<SpecialOfferDTO>?
)
