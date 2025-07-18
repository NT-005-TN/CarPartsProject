package ru.anasttruh.cproject.car

class CarRepository(private val carDao: CarDao) {
    suspend fun getAllCars() = carDao.getAllCars()
    suspend fun getCarById(id: Int) = carDao.getCarById(id)
    suspend fun insertCar(car: CarEntity) = carDao.insertCar(car)
    suspend fun updateCar(car: CarEntity) = carDao.updateCar(car)
    suspend fun deleteCar(car: CarEntity) = carDao.deleteCar(car)
}