package ru.anasttruh.cproject.db.dbCars

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.anasttruh.cproject.db.dbCars.CarEntity

@Dao
interface CarDao {
    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<CarEntity>

    @Query("SELECT * FROM Cars WHERE id = :carId")
    suspend fun getCarById(carId: Int): CarEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertCar(car: CarEntity)

    @Update
    suspend fun updateCar(car: CarEntity)

    @Delete
    suspend fun deleteCar(car: CarEntity)
}