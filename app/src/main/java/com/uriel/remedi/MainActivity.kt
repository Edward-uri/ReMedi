package com.uriel.remedi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.compose.AppTheme
import com.uriel.remedi.core.di.AppContainer
import com.uriel.remedi.features.medicine.di.MedicineModule
import com.uriel.remedi.features.medicine.presentation.screens.MedicineScreen

class MainActivity : ComponentActivity() {

    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContainer = AppContainer(this)

        val medicineModule = MedicineModule(appContainer)

        enableEdgeToEdge()
        setContent {
            AppTheme {
                MedicineScreen(
                    factory = medicineModule.provideMedicineViewModelFactory()
                )
            }
        }
    }
}