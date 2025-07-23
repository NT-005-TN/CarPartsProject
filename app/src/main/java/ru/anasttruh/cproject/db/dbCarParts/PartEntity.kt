package ru.anasttruh.cproject.db.dbCarParts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Parts")
data class PartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val carId: Int,
    val name: String,
    val installDate: String,       // YYYY-MM-DD
    val initialMileage: Int,       // пробег машины при установке
    val lifespanDays: Int,         // срок службы в днях
    val lifespanMileage: Int,      // срок службы в км
    val count: Int,                // количество установленных одинаковых деталей
    val wearPercent: Int           // износ в процентах
)
