package ru.anasttruh.cproject.carParts.editPart

import ru.anasttruh.cproject.db.dbCarParts.PartEntity

interface PartEditContract {
    interface View {
        fun showPart(part: PartEntity)
        fun showError(message: String)
        fun finishScreen()
    }

    interface Presenter {
        fun loadPart(partId: Int)
        fun savePart(
            carId: Int,
            partId: Int?,
            name: String,
            installDate: String,
            initialMileage: Int,
            count: Int,
            currentMileage: Int
        )
        fun onDestroy()
    }
}