package com.uriel.remedi.core.di

import android.content.Context
import androidx.room.Room
import com.uriel.remedi.BuildConfig
import com.uriel.remedi.core.database.AppDatabase
import com.uriel.remedi.core.network.MedicineApi
import com.uriel.remedi.features.medicine.data.repository.MedicineRepositoryImpl
import com.uriel.remedi.features.medicine.domain.repositories.MedicineRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(private val context: Context) {
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "remedi_database"
        ).build()
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val medicineApi: MedicineApi by lazy {
        retrofit.create(MedicineApi::class.java)
    }

    val medicineRepository: MedicineRepository by lazy {
        MedicineRepositoryImpl(
            medicineDao = database.medicineDao(),
            api = medicineApi
        )
    }
}