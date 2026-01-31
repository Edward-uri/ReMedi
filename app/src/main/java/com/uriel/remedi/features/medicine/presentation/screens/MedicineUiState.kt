package com.uriel.remedi.features.medicine.presentation.screens

import com.uriel.remedi.features.medicine.domain.entities.Medicine

data class MedicineUiState(
    val myMedicines: List<Medicine> = emptyList(),
    val searchResults: List<Medicine> = emptyList(),

    val isLoading: Boolean = false,
    val error: String? = null,

    val isSearching: Boolean = false
)