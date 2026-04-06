package com.example.familybudget.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.familybudget.data.model.Transaction
import com.example.familybudget.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(
    private val onItemClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private var items = listOf<Transaction>()

    fun submitList(list: List<Transaction>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.tvCategory.text = "Категория ${transaction.categoryId}" // временно, потом подставим имя
            val amount = transaction.amount / 100.0
            val sign = if (transaction.type == "income") "+" else "-"
            binding.tvAmount.text = "$sign${"%.2f".format(amount)} ₽"
            val date = SimpleDateFormat("dd.MM", Locale.getDefault()).format(Date(transaction.date))
            binding.tvDate.text = date
            binding.root.setOnClickListener { onItemClick(transaction) }
        }
    }
}
