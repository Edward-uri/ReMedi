package com.uriel.remedi.features.medicine.data.repository

import com.uriel.remedi.core.network.MedicineApi
import com.uriel.remedi.features.medicine.data.local.dao.MedicineDao
import com.uriel.remedi.features.medicine.data.mapper.toDomain
import com.uriel.remedi.features.medicine.data.mapper.toEntity
import com.uriel.remedi.features.medicine.domain.entities.Medicine
import com.uriel.remedi.features.medicine.domain.repositories.MedicineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedicineRepositoryImpl(
    private val medicineDao: MedicineDao,
    private val api: MedicineApi
) : MedicineRepository {

    override fun getAllMedicines(): Flow<List<Medicine>> {
        return medicineDao.getAllMedicines().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertMedicine(medicine: Medicine) {
        medicineDao.insertMedicine(medicine.toEntity())
    }

    override suspend fun deleteMedicine(medicine: Medicine) {
        medicineDao.deleteMedicine(medicine.toEntity())
    }

    override suspend fun searchMedicinesOnline(query: String): List<Medicine> {
        return try {
            val searchString = "openfda.brand_name:\"$query\""

            val response = api.searchMedicines(query = searchString)

            response.results?.map { termDto ->
                Medicine(
                    name = termDto.name.uppercase(),

                    dose = "",
                    frequencyHours = 8
                )
            } ?: emptyList()

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}