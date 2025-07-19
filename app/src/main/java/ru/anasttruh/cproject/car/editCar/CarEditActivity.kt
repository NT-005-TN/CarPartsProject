package ru.anasttruh.cproject.car.editCar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.anasttruh.cproject.car.addCar.CarRepository
import ru.anasttruh.cproject.databinding.ActivityCarEditBinding
import ru.anasttruh.cproject.databinding.ActivityCarListBinding
import ru.anasttruh.cproject.db.AppDatabase
import ru.anasttruh.cproject.db.dbCars.CarEntity

class CarEditActivity : AppCompatActivity(), CarEditContract.View {

    private lateinit var binding: ActivityCarEditBinding
    private lateinit var presenter: CarEditContract.Presenter
    private var carId: Int? = null

    override fun onCreate(savedInstantState: Bundle?) {
        super.onCreate(savedInstantState)
        binding = ActivityCarEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getDatabase(this).carDao()
        presenter = CarEditPresenter(this, CarRepository(dao))

        carId = intent.getIntExtra("carId", -1).takeIf { it != -1 }

        carId?.let {
            presenter.loadCar(it)
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val brand = binding.etBrand.text.toString()
            val mileage = binding.etMileage.text.toString().toIntOrNull() ?: -1

            presenter.saveCar(carId, name, brand, mileage)
        }
    }

    override fun showCar(car: CarEntity) {
        binding.etName.setText(car.name)
        binding.etBrand.setText(car.brand)
        binding.etMileage.setText(car.mileage.toString())
    }

    override fun showMessage(message: String) {
       Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun closeScreen() {
        finish()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }



}