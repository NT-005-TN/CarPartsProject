package ru.anasttruh.cproject.db.dbCarParts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

@Dao
interface PartDao {
    @Query("SELECT * FROM Parts WHERE carId = :carId")
    suspend fun getPartsByCarId(carId: Int): List<PartEntity>

    @Query("SELECT * FROM Parts WHERE id = :id")
    suspend fun getPartById(id: Int): PartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPart(part: PartEntity)

    @Update
    suspend fun updatePart(part: PartEntity)

    @Delete
    suspend fun deletePart(part: PartEntity)
}