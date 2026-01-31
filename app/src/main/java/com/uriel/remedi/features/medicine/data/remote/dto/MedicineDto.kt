package com.uriel.remedi.features.medicine.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MedicineResponse(
    @SerializedName("results") val results: List<MedicineTermDto>? = emptyList()
)

data class MedicineTermDto(
    @SerializedName("term") val name: String,

    @SerializedName("count") val count: Int?
)