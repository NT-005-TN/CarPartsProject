package ru.anasttruh.cproject.carParts.addPart

import ru.anasttruh.cproject.db.dbCarParts.PartEntity

interface PartListContract {
    interface View {
        fun showParts(parts: List<PartEntity>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadParts(carId: Int)
        fun deletePart(part: PartEntity)
        fun onSavePart(part: PartEntity)
    }
}