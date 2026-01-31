package com.uriel.remedi.features.medicine.presentation.screens

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uriel.remedi.core.receiver.AlarmReceiver
import com.uriel.remedi.features.medicine.domain.entities.Medicine
import com.uriel.remedi.features.medicine.presentation.components.AddMedicineDialog
import com.uriel.remedi.features.medicine.presentation.components.MedicineCard
import com.uriel.remedi.features.medicine.presentation.viewmodels.MedicineViewModel
import com.uriel.remedi.features.medicine.presentation.viewmodels.MedicineViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreen(
    factory: MedicineViewModelFactory
) {
    val viewModel: MedicineViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MediTime", fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                uiState.isLoading && !uiState.isSearching -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Warning, null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(48.dp))
                        Text("Error de conexión", color = MaterialTheme.colorScheme.error)
                    }
                }
                uiState.myMedicines.isEmpty() -> {
                    Text("No tienes alarmas activas", modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        items(uiState.myMedicines) { medicine ->
                            MedicineCard(
                                medicine = medicine,
                                onDeleteClick = { viewModel.deleteMedicine(medicine) }
                            )
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AddMedicineDialog(
                uiState = uiState,
                onDismiss = {
                    showAddDialog = false
                    viewModel.searchMedicine("")
                },
                onSearch = { query ->
                    viewModel.searchMedicine(query)
                },
                onSave = { name, dose, freqHours, secondsAlarm ->

                    viewModel.saveMedicine(Medicine(name = name, dose = dose, frequencyHours = freqHours))

                    try {
                        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        val intent = Intent(context, AlarmReceiver::class.java).apply {
                            putExtra("MED_NAME", name)
                        }

                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            name.hashCode(),
                            intent,
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        )

                        val triggerTime = System.currentTimeMillis() + (secondsAlarm * 1000)

                        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    showAddDialog = false
                }
            )
        }
    }
}