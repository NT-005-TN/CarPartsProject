package ru.anasttruh.cproject.car.addCar

import ru.anasttruh.cproject.db.dbCars.CarEntity

interface CarListContract {
    interface View {
        fun showCars(cars: List<CarEntity>)
        fun showMessage(message: String)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadCars()
        fun deleteCar(car: CarEntity)
        fun onDestroy()
    }
}