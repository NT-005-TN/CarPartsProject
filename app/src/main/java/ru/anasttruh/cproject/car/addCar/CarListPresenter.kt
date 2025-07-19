package ru.anasttruh.cproject.car.addCar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.anasttruh.cproject.db.dbCars.CarEntity

class CarListPresenter(
    private val view: CarListContract.View,
    private val repository: CarRepository
) : CarListContract.Presenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun loadCars() {
        scope.launch {
            try {
                val cars = repository.getAllCars()
                view.showCars(cars)
            } catch (e: Exception) {
                view.showError("Ошибка при загрузке машин: ${e.message}")
            }
        }
    }

    override fun deleteCar(car: CarEntity) {
        scope.launch {
            try {
                repository.deleteCar(car)
                loadCars()
                view.showMessage("Машина удалена")
            } catch (e: Exception) {
                view.showError("Ошибка при удалении: ${e.message}")
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
    }

}