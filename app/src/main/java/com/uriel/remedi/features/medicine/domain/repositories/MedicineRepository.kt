package com.uriel.remedi.features.medicine.domain.repositories

import com.uriel.remedi.features.medicine.domain.entities.Medicine
import kotlinx.coroutines.flow.Flow

interface MedicineRepository {
    //flow actualiza el estado si la basse de datos local cambia o se actualiza
    fun getAllMedicines(): Flow<List<Medicine>>
    suspend fun insertMedicine(medicine: Medicine)
    suspend fun deleteMedicine(medicine: Medicine)
    suspend fun searchMedicinesOnline(query: String): List<Medicine>
}