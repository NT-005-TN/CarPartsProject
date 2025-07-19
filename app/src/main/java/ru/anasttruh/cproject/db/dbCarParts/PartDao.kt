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

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(part: PartEntity)

    @Update
    suspend fun update(part: PartEntity)

    @Delete
    suspend fun delete(part: PartEntity)
}