package ru.anasttruh.cproject.car.addCar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anasttruh.cproject.car.editCar.CarEditActivity
import ru.anasttruh.cproject.carParts.addPart.PartListActivity
import ru.anasttruh.cproject.databinding.ActivityCarListBinding
import ru.anasttruh.cproject.db.AppDatabase
import ru.anasttruh.cproject.db.dbCars.CarEntity

class CarListActivity: AppCompatActivity(), CarListContract.View {

    private lateinit var binding: ActivityCarListBinding
    private lateinit var presenter: CarListContract.Presenter
    private lateinit var adapter: CarListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getDatabase(this).carDao()
        val repository = CarRepository(dao)
        presenter = CarListPresenter(this, repository)

        adapter = CarListAdapter (
            onCarClick = {
                car ->
                val intent = Intent(this, CarEditActivity::class.java)
                intent.putExtra("carId", car.id)
                startActivity(intent)
            },
            onDeleteClick = { car ->
                AlertDialog.Builder(this)
                    .setTitle("Удалить машину?")
                    .setMessage("Вы уверены, что хотите удалить '${car.name}', ${car.brand} с пробегом ${car.mileage} км?")
                    .setPositiveButton("Да") { _, _ ->
                        presenter.deleteCar(car)
                    }
                    .setNegativeButton("Отмена", null)
                    .show()
            },
            onDetailsClick = { car ->
                val intent = Intent(this, PartListActivity::class.java)
                intent.putExtra("carId", car.id)
                startActivity(intent)
            }

        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.fabAddCar.setOnClickListener {
            val intent = Intent(this, CarEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCars()
    }

    override fun showCars(cars: List<CarEntity>) {
        adapter.submitList(cars)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}