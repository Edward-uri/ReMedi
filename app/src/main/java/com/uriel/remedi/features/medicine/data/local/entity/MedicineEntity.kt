package com.uriel.remedi.features.medicine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "medicines")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val dose: String,
    val frequencyHours: Int,
    val startDate: Date,
    val isReminderActive: Boolean
)