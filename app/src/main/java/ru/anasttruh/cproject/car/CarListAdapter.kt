package ru.anasttruh.cproject.car

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.anasttruh.cproject.databinding.ItemCarBinding

class CarListAdapter (
    private val onCarClick: (CarEntity) -> Unit
) : ListAdapter<CarEntity, CarListAdapter.CarViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context))
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarViewHolder(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(car: CarEntity) {
            binding.textName.text = car.name
            binding.textBrand.text = car.brand
            binding.textMileage.text = "Пробег: ${car.mileage} км"
        binding.root.setOnClickListener { onCarClick(car) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CarEntity>() {
        override fun areItemsTheSame(oldItem: CarEntity, newItem: CarEntity) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CarEntity, newItem: CarEntity) =
            oldItem == newItem

    }
}