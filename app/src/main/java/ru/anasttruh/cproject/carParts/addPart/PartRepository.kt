package ru.anasttruh.cproject.carParts.addPart

import ru.anasttruh.cproject.db.AppDatabase
import ru.anasttruh.cproject.db.dbCarParts.PartDao
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartRepository(private val partDao: PartDao) {
    suspend fun getPartsByCarId(carId: Int) = partDao.getPartsByCarId(carId)
    suspend fun getPartById(id: Int) = partDao.getPartById(id)
    suspend fun insertPart(part: PartEntity) = partDao.insertPart(part)
    suspend fun updatePart(part: PartEntity) = partDao.updatePart(part)
    suspend fun deletePart(part: PartEntity) = partDao.deletePart(part)

}