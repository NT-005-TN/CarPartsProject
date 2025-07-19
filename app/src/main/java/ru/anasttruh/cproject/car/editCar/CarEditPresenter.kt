package ru.anasttruh.cproject.car.editCar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.anasttruh.cproject.car.addCar.CarRepository
import ru.anasttruh.cproject.db.dbCars.CarEntity

class CarEditPresenter(
    private val view: CarEditContract.View,
    private val repository: CarRepository
) : CarEditContract.Presenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun loadCar(carId: Int) {
        scope.launch {
            val car = repository.getCarById(carId)
            if (car != null) view.showCar(car)
            else view.showMessage("Машина не найдена")
        }
    }

    override fun saveCar(carId: Int?, name: String, brand: String, mileage: Int) {
        if (name.isBlank() || brand.isBlank() || mileage < 0) {
            view.showMessage("Заполните все поля корректно")
            return
        }

        scope.launch {
            val car = CarEntity(id = carId ?: 0, name = name, brand = brand, mileage = mileage)
            if (carId == null) repository.insertCar(car)
            else repository.updateCar(car)

            view.showMessage("Сохранено")
            view.closeScreen()
        }
    }

    override fun onDestroy() {
        job.cancel()
    }

}