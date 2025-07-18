package ru.anasttruh.cproject.car

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cars")
data class CarEntity(
    @PrimaryKey(autoGenerate = true) val id: Int =  0,
    val name: String,
    val brand: String,
    val mileage: Int
)