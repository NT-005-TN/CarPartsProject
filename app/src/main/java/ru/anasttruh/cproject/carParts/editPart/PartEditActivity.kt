package ru.anasttruh.cproject.carParts.editPart

import android.R
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ru.anasttruh.cproject.carParts.addPart.partsSpecs
import ru.anasttruh.cproject.databinding.ActivityPartEditBinding
import ru.anasttruh.cproject.db.AppDatabase
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartEditActivity : AppCompatActivity(), PartEditContract.View {

    private lateinit var binding: ActivityPartEditBinding
    private lateinit var presenter: PartEditContract.Presenter

    private var carId = 0
    private var partId: Int? = null
    private var currentMileage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carId = intent.getIntExtra("car_id", 0)
        partId = intent.getIntExtra("part_id", 0).takeIf { it != 0 }

        presenter = PartEditPresenter(this, AppDatabase.Companion.getDatabase(this))

        setupPartNameSpinner()
        setupInstallDatePicker()
        setupSaveButton()

        // Загрузка пробега и детали
        // Можно через Presenter, но пробег сейчас через локальную функцию
        lifecycleScope.launchWhenCreated {
            currentMileage = getCurrentCarMileage(carId)
            partId?.let { presenter.loadPart(it) }
        }
    }

    private fun setupPartNameSpinner() {
        val names = partsSpecs.map { it.name }
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, names)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerPartName.adapter = adapter
    }

    private fun setupInstallDatePicker() {
        binding.editTextInstallDate.setOnClickListener {
            val c = Calendar.getInstance()
            val dpd = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val dateStr = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                binding.editTextInstallDate.setText(dateStr)
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }
    }

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            val name = binding.spinnerPartName.selectedItem as String
            val installDate = binding.editTextInstallDate.text.toString()
            val initialMileage = binding.editTextInitialMileage.text.toString().toIntOrNull() ?: 0
            val count = binding.editTextCount.text.toString().toIntOrNull() ?: 1

            presenter.savePart(carId, partId, name, installDate, initialMileage, count, currentMileage)
        }
    }

    private suspend fun getCurrentCarMileage(carId: Int): Int {
        // TODO: получить пробег из базы, сейчас заглушка
        return 50000
    }

    // ======== PartEditContract.View ========
    override fun showPart(part: PartEntity) {
        binding.spinnerPartName.setSelection(partsSpecs.indexOfFirst { it.name == part.name })
        binding.editTextInstallDate.setText(part.installDate)
        binding.editTextInitialMileage.setText(part.initialMileage.toString())
        binding.editTextCount.setText(part.count.toString())
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun finishScreen() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}