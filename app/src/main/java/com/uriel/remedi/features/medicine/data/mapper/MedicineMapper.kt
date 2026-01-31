package com.uriel.remedi.features.medicine.data.mapper

import com.uriel.remedi.features.medicine.data.local.entity.MedicineEntity
import com.uriel.remedi.features.medicine.domain.entities.Medicine

fun MedicineEntity.toDomain(): Medicine {
    return Medicine(
        id = this.id,
        name = this.name,
        dose = this.dose,
        frequencyHours = this.frequencyHours,
        startDate = this.startDate,
        isReminderActive = this.isReminderActive
    )
}

fun Medicine.toEntity(): MedicineEntity {
    return MedicineEntity(
        id = this.id,
        name = this.name,
        dose = this.dose,
        frequencyHours = this.frequencyHours,
        startDate = this.startDate,
        isReminderActive = this.isReminderActive
    )
}