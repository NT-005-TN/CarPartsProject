package ru.anasttruh.cproject.carParts.addPart

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.anasttruh.cproject.db.AppDatabase
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartListPresenter(
    private val view: PartListContract.View,
    private val repository: PartRepository
) : PartListContract.Presenter {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun loadParts(carId: Int) {
        scope.launch {
            try {
                val parts = withContext(Dispatchers.IO) {
                    repository.getPartsByCarId(carId)
                }
                view.showParts(parts)
            } catch (e: Exception) {
                view.showMessage("Ошибка загрузки деталей: ${e.message}")
            }
        }
    }

    override fun deletePart(part: PartEntity) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) { repository.deletePart(part) }
                loadParts(part.carId)
                view.showMessage("Деталь удалена")
            } catch (e: Exception) {
                view.showMessage("Ошибка удаления: ${e.message}")
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
    }
}
