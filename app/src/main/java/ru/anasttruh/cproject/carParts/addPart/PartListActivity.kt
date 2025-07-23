package ru.anasttruh.cproject.carParts.addPart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anasttruh.cproject.carParts.editPart.PartEditActivity

import ru.anasttruh.cproject.databinding.ActivityPartListBinding
import ru.anasttruh.cproject.db.AppDatabase
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartListActivity : AppCompatActivity(), PartListContract.View {

    private lateinit var presenter: PartListContract.Presenter
    private lateinit var binding: ActivityPartListBinding
    private lateinit var adapter: PartAdapter
    private var carId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carId = intent.getIntExtra("car_id", 0)

        val db = AppDatabase.getDatabase(this)
        val repository = PartRepository(db.partDao())
        presenter = PartListPresenter(this, repository)

        adapter = PartAdapter(
            onDelete = { presenter.deletePart(it) },
            onEdit = { openEditPart(it) }
        )

        binding.recyclerViewParts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewParts.adapter = adapter

        binding.fabAddPart.setOnClickListener { openEditPart(null) }

        presenter.loadParts(carId)
    }

    private fun openEditPart(part: PartEntity?) {
        val intent = Intent(this, PartEditActivity::class.java).apply {
            putExtra("car_id", carId)
            part?.let { putExtra("part_id", it.id) }
        }
        startActivity(intent)
    }

    override fun showParts(parts: List<PartEntity>) {
        adapter.submitList(parts)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadParts(carId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
