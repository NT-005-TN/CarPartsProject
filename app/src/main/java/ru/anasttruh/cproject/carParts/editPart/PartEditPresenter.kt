package ru.anasttruh.cproject.carParts.editPart

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.anasttruh.cproject.carParts.editPart.PartEditContract
import ru.anasttruh.cproject.carParts.addPart.calculateWearPercent
import ru.anasttruh.cproject.carParts.addPart.partsSpecs
import ru.anasttruh.cproject.db.AppDatabase
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartEditPresenter(
    private val view: PartEditContract.View,
    private val db: AppDatabase
) : PartEditContract.Presenter {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun loadPart(partId: Int) {
        scope.launch {
            try {
                val part = withContext(Dispatchers.IO) { db.partDao().getPartById(partId) }
                if (part != null) {
                    view.showPart(part)
                } else {
                    view.showError("Деталь не найдена")
                }
            } catch (e: Exception) {
                view.showError("Ошибка загрузки детали: ${e.message}")
            }
        }
    }

    override fun savePart(
        carId: Int,
        partId: Int?,
        name: String,
        installDate: String,
        initialMileage: Int,
        count: Int,
        currentMileage: Int
    ) {
        val specs = partsSpecs.find { it.name == name }
        if (specs == null) {
            view.showError("Выберите корректную деталь")
            return
        }
        val wear = calculateWearPercent(installDate, initialMileage, currentMileage, specs)

        val part = PartEntity(
            id = partId ?: 0,
            carId = carId,
            name = name,
            installDate = installDate,
            initialMileage = initialMileage,
            lifespanDays = specs.lifespanRange.last * 365,
            lifespanMileage = specs.mileageRange.last,
            count = count,
            wearPercent = wear
        )

        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    if (partId == null) {
                        db.partDao().insertPart(part)
                    } else {
                        db.partDao().updatePart(part)
                    }
                }
                view.finishScreen()
            } catch (e: Exception) {
                view.showError("Ошибка сохранения: ${e.message}")
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
    }
}