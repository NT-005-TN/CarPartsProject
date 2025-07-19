package ru.anasttruh.cproject.carParts.addPart

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartListPresenter(
    private val view: PartListContract.View,
    private val repository: PartRepository
): PartListContract.Presenter {

    override fun loadParts(carId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val parts = repository.getPartsForCar(carId)
                withContext(Dispatchers.Main) {
                    view.showParts(parts)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showError("Ошибка загрузки: ${e.message}")
                }
            }
        }
    }

    override fun deletePart(part: PartEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deletePart(part)
            loadParts(part.carId)

        }
    }
}