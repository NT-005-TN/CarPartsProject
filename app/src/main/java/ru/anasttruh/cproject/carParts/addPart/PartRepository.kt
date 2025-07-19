package ru.anasttruh.cproject.carParts.addPart

import ru.anasttruh.cproject.db.dbCarParts.PartDao
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartRepository(private val partDao: PartDao) {
    suspend fun getPartsForCar(carId: Int) = partDao.getPartsByCarId(carId)
    suspend fun addPart(part: PartEntity) = partDao.insert(part)
    suspend fun updatePart(part: PartEntity) = partDao.update(part)
    suspend fun deletePart(part: PartEntity) = partDao.delete(part)
}