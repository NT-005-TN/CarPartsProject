package ru.anasttruh.cproject.car

interface CarListContract {
    interface View {
        fun showCars(cars: List<CarEntity>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadCars()
        fun onDestroy()
    }
}