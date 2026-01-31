package com.uriel.remedi.features.medicine.di

import com.uriel.remedi.core.di.AppContainer
import com.uriel.remedi.features.medicine.domain.usecases.DeleteMedicineUseCase
import com.uriel.remedi.features.medicine.domain.usecases.GetMedicinesUseCase
import com.uriel.remedi.features.medicine.domain.usecases.InsertMedicineUseCase
import com.uriel.remedi.features.medicine.domain.usecases.SearchMedicineUseCase
import com.uriel.remedi.features.medicine.presentation.viewmodels.MedicineViewModelFactory

class MedicineModule(private val appContainer: AppContainer) {

    private fun provideGetMedicinesUseCase(): GetMedicinesUseCase {
        return GetMedicinesUseCase(appContainer.medicineRepository)
    }

    private fun provideInsertMedicineUseCase(): InsertMedicineUseCase {
        return InsertMedicineUseCase(appContainer.medicineRepository)
    }

    private fun provideDeleteMedicineUseCase(): DeleteMedicineUseCase {
        return DeleteMedicineUseCase(appContainer.medicineRepository)
    }

    private fun provideSearchMedicineUseCase(): SearchMedicineUseCase {
        return SearchMedicineUseCase(appContainer.medicineRepository)
    }

    fun provideMedicineViewModelFactory(): MedicineViewModelFactory {
        return MedicineViewModelFactory(
            getMedicinesUseCase = provideGetMedicinesUseCase(),
            insertMedicineUseCase = provideInsertMedicineUseCase(),
            deleteMedicineUseCase = provideDeleteMedicineUseCase(),
            searchMedicineUseCase = provideSearchMedicineUseCase()
        )
    }
}