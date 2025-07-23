package ru.anasttruh.cproject.carParts.addPart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.anasttruh.cproject.databinding.ItemPartBinding
import ru.anasttruh.cproject.db.dbCarParts.PartEntity

class PartAdapter(
    private val onDelete: (PartEntity) -> Unit,
    private val onEdit: (PartEntity) -> Unit
) : ListAdapter<PartEntity, PartAdapter.PartViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartViewHolder {
        val binding = ItemPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PartViewHolder(private val binding: ItemPartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(part: PartEntity) {
            binding.textViewName.text = part.name
            binding.textViewWear.text = "Износ: ${part.wearPercent}%"
            binding.textViewCount.text = "Кол-во: ${part.count}"
            binding.textViewInstallDate.text = "Установлена: ${part.installDate}"
            binding.textViewMileage.text = "Пробег при установке: ${part.initialMileage} км"

            binding.buttonDelete.setOnClickListener { onDelete(part) }
            binding.root.setOnClickListener { onEdit(part) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PartEntity>() {
        override fun areItemsTheSame(oldItem: PartEntity, newItem: PartEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PartEntity, newItem: PartEntity) = oldItem == newItem
    }
}
