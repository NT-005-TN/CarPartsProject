package ru.anasttruh.cproject.carParts.addPart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.anasttruh.cproject.db.AppDatabase
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartListActivity : AppCompatActivity(), PartListContract.View {

    private lateinit var binding: ActivityPartListBinding
    private lateinit var presenter: PartListPresenter
    private lateinit var adapter: PartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartListBinding.infalte(layoutInflater)
        setContentView(binding.root)

        val carId = intent.getIntExtra("carId", -1)
        presnter = PartListPresenter(thos, PartRepository(AppDatabase.getDatabase(this).partDao()))
    }

    override fun showParts(parts: List<PartEntity>) {

    }

    override fun showError(message: String) {

    }
}