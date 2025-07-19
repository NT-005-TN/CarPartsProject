package ru.anasttruh.cproject.car.editCar

import ru.anasttruh.cproject.db.dbCars.CarEntity

interface CarEditContract {

    interface View {
        fun showCar(car: CarEntity)
        fun showMessage(message: String)
        fun closeScreen()
    }

    interface Presenter {
        fun loadCar(carId: Int)
        fun saveCar(carId: Int?, name: String, brand: String, mileage: Int)
        fun onDestroy()
    }

}