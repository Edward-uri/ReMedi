package com.uriel.remedi.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uriel.remedi.features.medicine.data.local.Converters
import com.uriel.remedi.features.medicine.data.local.dao.MedicineDao
import com.uriel.remedi.features.medicine.data.local.entity.MedicineEntity

@Database(
    entities = [MedicineEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun medicineDao(): MedicineDao
}