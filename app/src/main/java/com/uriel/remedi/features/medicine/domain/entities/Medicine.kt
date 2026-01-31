package com.uriel.remedi.features.medicine.domain.entities

import java.util.Date

data class Medicine(
    val id: Int = 0,
    val name: String,
    val dose: String,
    val frequencyHours: Int,
    val startDate: Date = Date(),
    val isReminderActive: Boolean = true
)
