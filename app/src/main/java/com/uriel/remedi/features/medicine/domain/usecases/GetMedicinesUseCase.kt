package com.uriel.remedi.features.medicine.domain.usecases

import com.uriel.remedi.features.medicine.domain.entities.Medicine
import com.uriel.remedi.features.medicine.domain.repositories.MedicineRepository
import kotlinx.coroutines.flow.Flow

class GetMedicinesUseCase(private val repository: MedicineRepository) {
    operator fun invoke(): Flow<List<Medicine>> {
        return repository.getAllMedicines()
    }
}