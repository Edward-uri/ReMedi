package com.uriel.remedi.features.medicine.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uriel.remedi.features.medicine.presentation.screens.MedicineUiState

@Composable
fun AddMedicineDialog(
    uiState: MedicineUiState,
    onDismiss: () -> Unit,
    onSearch: (String) -> Unit,
    onSave: (String, String, Int, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var dose by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var seconds by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Medicamento") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        onSearch(it)
                    },
                    label = { Text("Nombre (Ej. Aspirin)") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (uiState.searchResults.isNotEmpty() && uiState.isSearching) {
                    Card(modifier = Modifier.fillMaxWidth().heightIn(max = 150.dp).padding(vertical = 4.dp)) {
                        LazyColumn {
                            items(uiState.searchResults) { result ->
                                Text(
                                    text = "${result.name} (${result.dose})",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            name = result.name
                                            dose = result.dose
                                            onSearch("")
                                        }
                                        .padding(10.dp)
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = dose,
                    onValueChange = { dose = it },
                    label = { Text("Dosis (ej. 500mg)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = frequency,
                    onValueChange = { frequency = it },
                    label = { Text("Frecuencia (Horas)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = seconds,
                    onValueChange = { seconds = it },
                    label = { Text("Alarma en (Segundos)") },
                    placeholder = { Text("Ej. 5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotEmpty() && seconds.isNotEmpty()) {
                        onSave(
                            name,
                            dose,
                            frequency.toIntOrNull() ?: 8,
                            seconds.toIntOrNull() ?: 10
                        )
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}