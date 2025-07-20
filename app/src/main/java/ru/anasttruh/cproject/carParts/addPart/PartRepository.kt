package ru.anasttruh.cproject.carParts.addPart

import ru.anasttruh.cproject.db.dbCarParts.PartDao
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartRepository(private val partDao: PartDao) {
    suspend fun getPartsForCar(carId: Int) = partDao.getPartsByCarId(carId)
    suspend fun getPart(id: Int) = partDao.getPartById(id)
    suspend fun add(part: PartEntity) = partDao.insert(part)
    suspend fun update(part: PartEntity) = partDao.update(part)
    suspend fun deletePart(part: PartEntity) = partDao.delete(part)

    fun getDefaultParts(): List<String> {
        return listOf(
            "Двигатель", "Масляный фильтр", "Топливный фильтр", "Свечи зажигания",
            "Аккумулятор", "Ремень ГРМ", "Тормозные колодки", "Сцепление", "Радиатор", "Амортизаторы"
        )
    }
}