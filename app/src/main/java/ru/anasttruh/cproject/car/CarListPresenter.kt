package ru.anasttruh.cproject.car

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    override fun onDestroy() {
        job.cancel()
    }

}