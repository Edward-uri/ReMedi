package com.uriel.remedi.features.medicine.domain.usecases

import com.uriel.remedi.features.medicine.domain.entities.Medicine
import com.uriel.remedi.features.medicine.domain.repositories.MedicineRepository

class DeleteMedicineUseCase(private val repository: MedicineRepository) {
    suspend operator fun invoke(medicine: Medicine) {
        repository.deleteMedicine(medicine)
    }
}