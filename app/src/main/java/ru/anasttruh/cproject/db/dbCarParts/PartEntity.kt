package ru.anasttruh.cproject.db.dbCarParts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Parts")
data class PartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val carId: Int,
    val name: String,
    val installationDate: Long,
    val mileageAtInstall: Int,
    val note: String?,
    val imageUrl: String?
)