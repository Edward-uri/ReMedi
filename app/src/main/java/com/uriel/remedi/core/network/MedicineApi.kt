package com.uriel.remedi.core.network

import com.uriel.remedi.features.medicine.data.remote.dto.MedicineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MedicineApi {
    @GET("drug/label.json")
    suspend fun searchMedicines(
        @Query("count") countField: String = "openfda.brand_name.exact",

        @Query("limit") limit: Int = 10,
        @Query("search") query: String
    ): MedicineResponse
}