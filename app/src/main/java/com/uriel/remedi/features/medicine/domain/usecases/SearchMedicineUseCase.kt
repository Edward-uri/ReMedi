package com.uriel.remedi.features.medicine.domain.usecases

import com.uriel.remedi.features.medicine.domain.entities.Medicine
import com.uriel.remedi.features.medicine.domain.repositories.MedicineRepository

class SearchMedicineUseCase(private val repository: MedicineRepository) {
    suspend operator fun invoke(query: String): List<Medicine> {
        if (query.isBlank()) return emptyList()
        return repository.searchMedicinesOnline(query)
    }
}