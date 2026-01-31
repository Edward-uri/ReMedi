package com.uriel.remedi.features.medicine.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uriel.remedi.features.medicine.domain.entities.Medicine
import com.uriel.remedi.features.medicine.domain.usecases.DeleteMedicineUseCase
import com.uriel.remedi.features.medicine.domain.usecases.GetMedicinesUseCase
import com.uriel.remedi.features.medicine.domain.usecases.InsertMedicineUseCase
import com.uriel.remedi.features.medicine.domain.usecases.SearchMedicineUseCase
import com.uriel.remedi.features.medicine.presentation.screens.MedicineUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MedicineViewModel(
    private val getMedicinesUseCase: GetMedicinesUseCase,
    private val insertMedicineUseCase: InsertMedicineUseCase,
    private val deleteMedicineUseCase: DeleteMedicineUseCase,
    private val searchMedicineUseCase: SearchMedicineUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MedicineUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMyMedicines()
    }

    private fun loadMyMedicines() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {

            getMedicinesUseCase()
                .catch { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
                .collect { list ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            myMedicines = list,
                            error = null
                        )
                    }
                }
        }
    }


    fun searchMedicine(query: String) {
        if (query.isBlank()) {
            _uiState.update { it.copy(isSearching = false, searchResults = emptyList()) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isSearching = true) }

            try {
                val results = searchMedicineUseCase(query)

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        searchResults = results,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = "Error buscando: ${e.message}"
                    )
                }
            }
        }
    }


    fun saveMedicine(medicine: Medicine) {
        viewModelScope.launch {
            try {
                insertMedicineUseCase(medicine)
                _uiState.update { it.copy(isSearching = false, searchResults = emptyList()) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "No se pudo guardar") }
            }
        }
    }

    fun deleteMedicine(medicine: Medicine) {
        viewModelScope.launch {
            deleteMedicineUseCase(medicine)
        }
    }
}