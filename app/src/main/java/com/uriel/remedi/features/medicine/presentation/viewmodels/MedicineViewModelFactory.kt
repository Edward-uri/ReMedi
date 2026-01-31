package com.uriel.remedi.features.medicine.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uriel.remedi.features.medicine.domain.usecases.DeleteMedicineUseCase
import com.uriel.remedi.features.medicine.domain.usecases.GetMedicinesUseCase
import com.uriel.remedi.features.medicine.domain.usecases.InsertMedicineUseCase
import com.uriel.remedi.features.medicine.domain.usecases.SearchMedicineUseCase

class MedicineViewModelFactory(
    private val getMedicinesUseCase: GetMedicinesUseCase,
    private val insertMedicineUseCase: InsertMedicineUseCase,
    private val deleteMedicineUseCase: DeleteMedicineUseCase,
    private val searchMedicineUseCase: SearchMedicineUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicineViewModel::class.java)) {
            return MedicineViewModel(
                getMedicinesUseCase,
                insertMedicineUseCase,
                deleteMedicineUseCase,
                searchMedicineUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}